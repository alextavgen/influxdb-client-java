/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.influxdata.client.internal;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.influxdata.Arguments;
import org.influxdata.client.SourcesApi;
import org.influxdata.client.domain.Bucket;
import org.influxdata.client.domain.Buckets;
import org.influxdata.client.domain.Check;
import org.influxdata.client.domain.Source;
import org.influxdata.client.domain.Sources;
import org.influxdata.client.service.SourcesService;
import org.influxdata.internal.AbstractRestClient;

import retrofit2.Call;

/**
 * @author Jakub Bednar (bednar@github) (18/09/2018 09:40)
 */
final class SourcesApiImpl extends AbstractRestClient implements SourcesApi {

    private static final Logger LOG = Logger.getLogger(SourcesApiImpl.class.getName());

    private final InfluxDBClientImpl influxDBClient;
    private final SourcesService service;

    SourcesApiImpl(@Nonnull final SourcesService service,
                   @Nonnull final InfluxDBClientImpl influxDBClient) {

        Arguments.checkNotNull(service, "service");
        Arguments.checkNotNull(influxDBClient, "influxDBClient");

        this.service = service;
        this.influxDBClient = influxDBClient;
    }

    @Nonnull
    @Override
    public Source createSource(@Nonnull final Source source) {

        Arguments.checkNotNull(source, "Source is required");

        Call<Source> call = service.sourcesPost(source, null);
        return execute(call);
    }

    @Nonnull
    @Override
    public Source updateSource(@Nonnull final Source source) {

        Arguments.checkNotNull(source, "Source is required");

        Call<Source> call = service.sourcesSourceIDPatch(source.getId(), source, null);
        return execute(call);
    }

    @Override
    public void deleteSource(@Nonnull final Source source) {

        Arguments.checkNotNull(source, "Source is required");

        deleteSource(source.getId());
    }

    @Override
    public void deleteSource(@Nonnull final String sourceID) {

        Arguments.checkNonEmpty(sourceID, "sourceID");

        Call<Void> call = service.sourcesSourceIDDelete(sourceID);
        execute(call);
    }

    @Nonnull
    @Override
    public Source cloneSource(@Nonnull final String clonedName, @Nonnull final String sourceID) {

        Arguments.checkNonEmpty(clonedName, "clonedName");
        Arguments.checkNonEmpty(sourceID, "sourceID");

        Source source = findSourceByID(sourceID);

        return cloneSource(clonedName, source);
    }

    @Nonnull
    @Override
    public Source cloneSource(@Nonnull final String clonedName, @Nonnull final Source source) {

        Arguments.checkNonEmpty(clonedName, "clonedName");
        Arguments.checkNotNull(source, "source");

        Source cloned = new Source();
        cloned.setName(clonedName);
        cloned.setOrgID(source.getOrgID());
        cloned.setDefault(source.isDefault());
        cloned.setType(source.getType());
        cloned.setUrl(source.getUrl());
        cloned.setInsecureSkipVerify(source.isInsecureSkipVerify());
        cloned.setTelegraf(source.getTelegraf());
        cloned.setToken(source.getToken());
        cloned.setUsername(source.getUsername());
        cloned.setPassword(source.getPassword());
        cloned.setSharedSecret(source.getSharedSecret());
        cloned.setMetaUrl(source.getMetaUrl());
        cloned.setDefaultRP(source.getDefaultRP());

        return createSource(cloned);
    }

    @Nonnull
    @Override
    public Source findSourceByID(@Nonnull final String sourceID) {

        Arguments.checkNonEmpty(sourceID, "sourceID");

        Call<Source> call = service.sourcesSourceIDGet(sourceID, null);

        return execute(call);
    }

    @Nonnull
    @Override
    public List<Source> findSources() {
        Call<Sources> sourcesCall = service.sourcesGet(null, null);

        Sources sources = execute(sourcesCall);
        LOG.log(Level.FINEST, "findSources found: {0}", sources);

        return sources.getSources();
    }

    @Nullable
    @Override
    public List<Bucket> findBucketsBySource(@Nonnull final Source source) {

        Arguments.checkNotNull(source, "Source is required");

        return findBucketsBySourceID(source.getId());
    }

    @Nonnull
    @Override
    public List<Bucket> findBucketsBySourceID(@Nonnull final String sourceID) {

        Arguments.checkNonEmpty(sourceID, "sourceID");

        Call<Buckets> call = service.sourcesSourceIDBucketsGet(sourceID, null, null);

        return execute(call).getBuckets();
    }

    @Nonnull
    @Override
    public Check health(@Nonnull final Source source) {

        Arguments.checkNotNull(source, "Source is required");

        return health(source.getId());
    }

    @Nonnull
    @Override
    public Check health(@Nonnull final String sourceID) {

        Arguments.checkNonEmpty(sourceID, "sourceID");

        return influxDBClient.health(service.sourcesSourceIDHealthGet(sourceID, null));
    }
}