package com.scot.restsvc;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override
    protected void configure() {
        bind(Service.class);
    }
}
