/*
 *
 *  Copyright 2018 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.genie.web.services.impl;

import com.netflix.genie.common.exceptions.GenieException;
import com.netflix.genie.common.internal.dto.v4.AgentClientMetadata;
import com.netflix.genie.common.internal.dto.v4.JobRequest;
import com.netflix.genie.common.internal.dto.v4.JobRequestMetadata;
import com.netflix.genie.common.internal.dto.v4.JobSpecification;
import com.netflix.genie.web.services.AgentJobService;
import com.netflix.genie.web.services.JobPersistenceService;
import com.netflix.genie.web.services.JobSpecificationService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Default implementation of {@link AgentJobService}.
 *
 * @author tgianos
 * @since 4.0.0
 */
@Validated
@Service
@Transactional
public class AgentJobServiceImpl implements AgentJobService {

    private final JobPersistenceService jobPersistenceService;
    private final JobSpecificationService jobSpecificationService;
    private final MeterRegistry meterRegistry;

    /**
     * Constructor.
     *
     * @param jobPersistenceService   The persistence service to use
     * @param jobSpecificationService The specification service to use
     * @param meterRegistry           The metrics registry to use
     */
    public AgentJobServiceImpl(
        final JobPersistenceService jobPersistenceService,
        final JobSpecificationService jobSpecificationService,
        final MeterRegistry meterRegistry
    ) {
        this.jobPersistenceService = jobPersistenceService;
        this.jobSpecificationService = jobSpecificationService;
        this.meterRegistry = meterRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String reserveJobId(
        @Valid final JobRequest jobRequest,
        @Valid final AgentClientMetadata agentClientMetadata
    ) throws GenieException {
        final JobRequestMetadata jobRequestMetadata = new JobRequestMetadata(null, agentClientMetadata, 0, 0);
        return this.jobPersistenceService.saveJobRequest(jobRequest, jobRequestMetadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobSpecification resolveJobSpecification(final String id) {
        return null;
    }
}
