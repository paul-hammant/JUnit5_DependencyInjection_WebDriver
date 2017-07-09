/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package com.example.project;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.logging.Logger;

public class WebDriverExtension implements ParameterResolver {

    private static final Logger LOG = Logger.getLogger(WebDriverExtension.class.getName());

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(WebDriver.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ExtensionContext.Store store = getRoot(extensionContext).getStore();
        return store.getOrComputeIfAbsent(WebDriver.class, webDriverClass -> {
            LOG.info("Creating ChromeDriver");
            ChromeDriver chromeDriver = new ChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOG.info("Quitting ChromeDriver");
                chromeDriver.quit();
            }));
            return chromeDriver;
        });
    }

    private ExtensionContext getRoot(ExtensionContext extensionContext) {
        ExtensionContext root = extensionContext;
        while (root.getParent().isPresent()) {
            root = root.getParent().get();
        }
        return root;
    }


}
