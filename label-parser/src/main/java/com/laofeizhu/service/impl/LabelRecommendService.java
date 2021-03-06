package com.laofeizhu.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.laofeizhu.config.KieSessionHelper;
import com.laofeizhu.enums.ReadFromType;
import com.laofeizhu.model.BaseProductVo;
import com.laofeizhu.model.BaseUserVo;
import com.laofeizhu.model.Result;
import com.laofeizhu.model.UserLabelVo;
import com.laofeizhu.service.IRecommendService;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Objects;

/**
 * @Classname LabelRecommendService
 * @Description TODO
 * @Date 2021/1/16 16:14
 * @Created by laofeizhu
 */
public class LabelRecommendService implements IRecommendService {

    private static final String PRODUCT_DRL_PATH = "rules/product/product.drl";
    private String filePath;
    private Boolean isReload;
    private String version;
    private ReadFromType fromType;
    private List<? extends BaseProductVo> baseProductVos;

    protected LabelRecommendService(String filePath, List<? extends BaseProductVo> baseProductVos, Boolean isReload) {
        this.filePath = filePath;
        this.baseProductVos = baseProductVos;
        this.isReload = Objects.isNull(isReload) ? false : isReload;
        this.fromType = ReadFromType.PATH;
    }

    protected LabelRecommendService(String version, String drlContent, List<? extends BaseProductVo> baseProductVos) {
        this.baseProductVos = baseProductVos;
        this.version = version;
        this.fromType = ReadFromType.CONTENT;
        if (drlContent != null && drlContent.trim().length() > 0) {
            KieSessionHelper.loadInVersion(version, drlContent);
        }
    }

    @Override
    public <T extends BaseProductVo> void addProduct(List<T> productVos) {
        if (this.baseProductVos != null && this.baseProductVos.size() > 0) {
            this.baseProductVos.addAll((List)productVos);
            this.baseProductVos = Lists.newArrayList(Sets.newHashSet(this.baseProductVos));
        } else {
            this.baseProductVos = Lists.newArrayList(Sets.newHashSet(productVos));
        }
    }

    @Override
    public <T extends BaseProductVo> void replaceProduct(List<T> baseProductVos) {
        if (baseProductVos!=null && baseProductVos.size()>0) {
            this.baseProductVos = Lists.newArrayList(Sets.newHashSet(baseProductVos));
        }
    }

    @Override
    public List<? extends BaseProductVo> listProduct() {
        return this.baseProductVos;
    }

    @Override
    public List<String> listMatchingLabel(List<UserLabelVo> userLabels) {
        if (Objects.nonNull(userLabels) && userLabels.size() > 0) {
            KieSession kieSession = null;
            switch (this.fromType) {
                case PATH:
                    kieSession = KieSessionHelper.getKieSessionByPath(filePath, isReload);
                    break;
                case CONTENT:
                    kieSession = KieSessionHelper.getKieSessionByVersion(version);
                    break;
                default:
                    kieSession = KieSessionHelper.getKieSessionByVersion(version);
                    break;
            }
            for (UserLabelVo userLabel : userLabels) {
                kieSession.insert(userLabel);
            }
            Result<String> result = new Result<String>();
            kieSession.insert(result);
            kieSession.fireAllRules();
            kieSession.dispose();
            return result.getResults();
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public <T extends BaseProductVo> List<T> listMatchingProduct(List<UserLabelVo> userLabels) {
        if (Objects.nonNull(baseProductVos) &&
                baseProductVos.size() > 0) {
            List<String> productLabels = listMatchingLabel(userLabels);
            if (Objects.nonNull(productLabels) &&
                    productLabels.size() > 0) {
                KieSession kieSession = KieSessionHelper.getKieSessionByPath(PRODUCT_DRL_PATH);
                Result<T> result = new Result<T>();
                for (String productLabel : productLabels) {
                    kieSession.insert(new BaseUserVo(productLabel));
                }
                for (BaseProductVo productVo : baseProductVos) {
                    kieSession.insert(productVo);
                }
                kieSession.insert(result);
                kieSession.fireAllRules();
                kieSession.dispose();
                return result.getResults();
            }
        }
        return Lists.newArrayList();
    }

    @Override
    public String getContent() {
        switch (this.fromType) {
            case PATH:
                return KieSessionHelper.getSessionContentByPath(filePath);
            case CONTENT:
                return KieSessionHelper.getSessionContentByVersion(version);
            default:
                return KieSessionHelper.getSessionContentByVersion(version);
        }
    }
}
