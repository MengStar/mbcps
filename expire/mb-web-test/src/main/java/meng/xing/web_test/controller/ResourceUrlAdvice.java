package meng.xing.web_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * 使得在ftl膜版中可以获取url；
 */
@ControllerAdvice
public class ResourceUrlAdvice {
    private final
    ResourceUrlProvider resourceUrlProvider;

    @Autowired
    public ResourceUrlAdvice(ResourceUrlProvider resourceUrlProvider) {
        this.resourceUrlProvider = resourceUrlProvider;
    }

    @ModelAttribute("urls")
    public ResourceUrlProvider urls() {
        return this.resourceUrlProvider;
    }
}
