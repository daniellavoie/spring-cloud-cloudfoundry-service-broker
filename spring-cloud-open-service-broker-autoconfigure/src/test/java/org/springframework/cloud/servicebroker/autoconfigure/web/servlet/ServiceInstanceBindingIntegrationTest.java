/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.servicebroker.autoconfigure.web.servlet;

import org.junit.Before;

import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.fixture.DataFixture;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class ServiceInstanceBindingIntegrationTest extends ControllerIntegrationTest {
	public static final String SERVICE_INSTANCE_BINDING_ID = "service-instance-binding-id";
	
	protected static final String SERVICE_INSTANCES_ROOT_PATH = "/v2/service_instances/";

	protected UriComponentsBuilder uriBuilder;
	protected UriComponentsBuilder cfInstanceIdUriBuilder;

	protected String createRequestBody;

	@Before
	public void setupBase() {
		uriBuilder = UriComponentsBuilder.fromPath(SERVICE_INSTANCES_ROOT_PATH)
				.pathSegment(SERVICE_INSTANCE_ID, "service_bindings", SERVICE_INSTANCE_BINDING_ID);
		cfInstanceIdUriBuilder = UriComponentsBuilder.fromPath("/").path(CF_INSTANCE_ID).path(SERVICE_INSTANCES_ROOT_PATH)
				.pathSegment(SERVICE_INSTANCE_ID, "service_bindings", SERVICE_INSTANCE_BINDING_ID);

		CreateServiceInstanceBindingRequest request = CreateServiceInstanceBindingRequest.builder()
				.serviceDefinitionId(serviceDefinition.getId())
				.planId("standard")
				.build();

		createRequestBody = DataFixture.toJson(request);
	}

	protected String buildCreateUrl(Boolean withCfInstanceId) {
		UriComponentsBuilder builder = withCfInstanceId ? cfInstanceIdUriBuilder : uriBuilder;
		return builder.toUriString();
	}

	protected String buildDeleteUrl(Boolean withCfInstanceId) {
		UriComponentsBuilder builder = withCfInstanceId ? cfInstanceIdUriBuilder : uriBuilder;
		return builder
				.queryParam("service_id", serviceDefinition.getId())
				.queryParam("plan_id", serviceDefinition.getPlans().get(0).getId())
				.toUriString();
	}
}
