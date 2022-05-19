package cloud.tianai.captcha.spring.plugins;

import cloud.tianai.captcha.generator.ImageCaptchaGeneratorProvider;
import cloud.tianai.captcha.generator.impl.MultiImageCaptchaGenerator;
import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import cloud.tianai.captcha.spring.autoconfiguration.ImageCaptchaProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @Author: 天爱有情
 * @date 2022/5/19 14:37
 * @Description 基于spring的 多验证码生成器
 */
public class SpringMultiImageCaptchaGenerator extends MultiImageCaptchaGenerator {
    private ListableBeanFactory beanFactory;

    public SpringMultiImageCaptchaGenerator(ImageCaptchaResourceManager imageCaptchaResourceManager,
                                            BeanFactory beanFactory) {
        super(imageCaptchaResourceManager);
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    protected void doInit(boolean initDefaultResource) {
        super.doInit(initDefaultResource);
        String[] beanNamesForType = beanFactory.getBeanNamesForType(ImageCaptchaGeneratorProvider.class);
        if (!ArrayUtils.isEmpty(beanNamesForType)) {
            for (String beanName : beanNamesForType) {
                ImageCaptchaGeneratorProvider provider = beanFactory.getBean(beanName, ImageCaptchaGeneratorProvider.class);
                addImageCaptchaGeneratorProvider(provider);
            }
        }
    }
}
