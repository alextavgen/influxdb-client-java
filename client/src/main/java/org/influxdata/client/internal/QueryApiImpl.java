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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.influxdata.Arguments;
import org.influxdata.Cancellable;
import org.influxdata.client.QueryApi;
import org.influxdata.client.domain.Dialect;
import org.influxdata.client.domain.Query;
import org.influxdata.client.service.QueryService;
import org.influxdata.internal.AbstractQueryApi;
import org.influxdata.query.FluxRecord;
import org.influxdata.query.FluxTable;
import org.influxdata.query.internal.FluxCsvParser;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author Jakub Bednar (bednar@github) (17/10/2018 10:50)
 */
final class QueryApiImpl extends AbstractQueryApi implements QueryApi {

    private static final Logger LOG = Logger.getLogger(QueryApiImpl.class.getName());

    private final QueryService service;

    QueryApiImpl(@Nonnull final QueryService service) {

        Arguments.checkNotNull(service, "service");

        this.service = service;
    }

    @Nonnull
    @Override
    public List<FluxTable> query(@Nonnull final String query, @Nonnull final String orgID) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");

        return query(new Query().query(query).dialect(AbstractInfluxDBClient.DEFAULT_DIALECT), orgID);
    }

    @Nonnull
    @Override
    public List<FluxTable> query(@Nonnull final Query query,
                                 @Nonnull final String orgID) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");

        FluxCsvParser.FluxResponseConsumerTable consumer = fluxCsvParser.new FluxResponseConsumerTable();

        query(query, orgID, consumer, ERROR_CONSUMER, EMPTY_ACTION, false);

        return consumer.getTables();
    }

    @Nonnull
    @Override
    public <M> List<M> query(@Nonnull final String query,
                             @Nonnull final String orgID,
                             @Nonnull final Class<M> measurementType) {

        Arguments.checkNonEmpty(query, "query");

        Query dialect = new Query().query(query).dialect(AbstractInfluxDBClient.DEFAULT_DIALECT);

        return query(dialect, orgID, measurementType);
    }

    @Nonnull
    @Override
    public <M> List<M> query(@Nonnull final Query query,
                             @Nonnull final String orgID,
                             @Nonnull final Class<M> measurementType) {

        Arguments.checkNotNull(query, "query");

        List<M> measurements = new ArrayList<>();

        FluxCsvParser.FluxResponseConsumer consumer = new FluxCsvParser.FluxResponseConsumer() {

            @Override
            public void accept(final int index,
                               @Nonnull final Cancellable cancellable,
                               @Nonnull final FluxTable table) {

            }

            @Override
            public void accept(final int index,
                               @Nonnull final Cancellable cancellable,
                               @Nonnull final FluxRecord record) {

                measurements.add(resultMapper.toPOJO(record, measurementType));
            }
        };

        query(query, orgID, consumer, ERROR_CONSUMER, EMPTY_ACTION, false);

        return measurements;
    }

    @Override
    public void query(@Nonnull final String query,
                      @Nonnull final String orgID,
                      @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNotNull(onNext, "onNext");

        query(query, orgID, onNext, ERROR_CONSUMER);
    }

    @Override
    public void query(@Nonnull final Query query,
                      @Nonnull final String orgID,
                      @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNotNull(onNext, "onNext");

        query(query, orgID, onNext, ERROR_CONSUMER);
    }

    @Override
    public <M> void query(@Nonnull final String query,
                          @Nonnull final String orgID,
                          @Nonnull final Class<M> measurementType,
                          @Nonnull final BiConsumer<Cancellable, M> onNext) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(measurementType, "measurementType");

        query(query, orgID, measurementType, onNext, ERROR_CONSUMER);
    }

    @Override
    public <M> void query(@Nonnull final Query query,
                          @Nonnull final String orgID,
                          @Nonnull final Class<M> measurementType,
                          @Nonnull final BiConsumer<Cancellable, M> onNext) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(measurementType, "measurementType");

        query(query, orgID, measurementType, onNext, ERROR_CONSUMER);
    }

    @Override
    public void query(@Nonnull final String query,
                      @Nonnull final String orgID,
                      @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext,
                      @Nonnull final Consumer<? super Throwable> onError) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");

        query(query, orgID, onNext, onError, EMPTY_ACTION);
    }

    @Override
    public void query(@Nonnull final Query query,
                      @Nonnull final String orgID,
                      @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext,
                      @Nonnull final Consumer<? super Throwable> onError) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");

        query(query, orgID, onNext, onError, EMPTY_ACTION);
    }

    @Override
    public <M> void query(@Nonnull final String query,
                          @Nonnull final String orgID,
                          @Nonnull final Class<M> measurementType,
                          @Nonnull final BiConsumer<Cancellable, M> onNext,
                          @Nonnull final Consumer<? super Throwable> onError) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(measurementType, "measurementType");

        query(query, orgID, measurementType, onNext, onError, EMPTY_ACTION);
    }

    @Override
    public <M> void query(@Nonnull final Query query,
                          @Nonnull final String orgID,
                          @Nonnull final Class<M> measurementType,
                          @Nonnull final BiConsumer<Cancellable, M> onNext,
                          @Nonnull final Consumer<? super Throwable> onError) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(measurementType, "measurementType");

        query(query, orgID, measurementType, onNext, onError, EMPTY_ACTION);
    }

    @Override
    public void query(@Nonnull final String query,
                      @Nonnull final String orgID,
                      @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext,
                      @Nonnull final Consumer<? super Throwable> onError,
                      @Nonnull final Runnable onComplete) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(onComplete, "onComplete");

        Query queryObj = new Query().query(query).dialect(AbstractInfluxDBClient.DEFAULT_DIALECT);

        query(queryObj, orgID, onNext, onError, onComplete);
    }

    @Override
    public void query(@Nonnull final Query query,
                      @Nonnull final String orgID,
                      @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext,
                      @Nonnull final Consumer<? super Throwable> onError,
                      @Nonnull final Runnable onComplete) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(onComplete, "onComplete");

        FluxCsvParser.FluxResponseConsumer consumer = new FluxCsvParser.FluxResponseConsumer() {

            @Override
            public void accept(final int index,
                               @Nonnull final Cancellable cancellable,
                               @Nonnull final FluxTable table) {
            }

            @Override
            public void accept(final int index,
                               @Nonnull final Cancellable cancellable,
                               @Nonnull final FluxRecord record) {
                onNext.accept(cancellable, record);
            }
        };

        query(query, orgID, consumer, onError, onComplete, true);
    }

    @Override
    public <M> void query(@Nonnull final String query,
                          @Nonnull final String orgID,
                          @Nonnull final Class<M> measurementType,
                          @Nonnull final BiConsumer<Cancellable, M> onNext,
                          @Nonnull final Consumer<? super Throwable> onError,
                          @Nonnull final Runnable onComplete) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(onComplete, "onComplete");
        Arguments.checkNotNull(measurementType, "measurementType");

        Query queryObj = new Query().query(query).dialect(AbstractInfluxDBClient.DEFAULT_DIALECT);

        query(queryObj, orgID, measurementType,  onNext, onError, onComplete);
    }

    @Override
    public <M> void query(@Nonnull final Query query,
                          @Nonnull final String orgID,
                          @Nonnull final Class<M> measurementType,
                          @Nonnull final BiConsumer<Cancellable, M> onNext,
                          @Nonnull final Consumer<? super Throwable> onError,
                          @Nonnull final Runnable onComplete) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onNext, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(onComplete, "onComplete");
        Arguments.checkNotNull(measurementType, "measurementType");

        FluxCsvParser.FluxResponseConsumer consumer = new FluxCsvParser.FluxResponseConsumer() {

            @Override
            public void accept(final int index,
                               @Nonnull final Cancellable cancellable,
                               @Nonnull final FluxTable table) {

            }

            @Override
            public void accept(final int index,
                               @Nonnull final Cancellable cancellable,
                               @Nonnull final FluxRecord record) {

                onNext.accept(cancellable, resultMapper.toPOJO(record, measurementType));

            }
        };

        query(query, orgID, consumer, onError, onComplete, true);
    }

    @Nonnull
    @Override
    public String queryRaw(@Nonnull final String query, @Nonnull final String orgID) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");

        return queryRaw(new Query().query(query), orgID);
    }

    @Nonnull
    @Override
    public String queryRaw(@Nonnull final String query,
                           @Nullable final Dialect dialect,
                           @Nonnull final String orgID) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");

        return queryRaw(new Query().query(query).dialect(dialect), orgID);
    }

    @Nonnull
    @Override
    public String queryRaw(@Nonnull final Query query, @Nonnull final String orgID) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");

        List<String> rows = new ArrayList<>();

        BiConsumer<Cancellable, String> consumer = (cancellable, row) -> rows.add(row);

        queryRaw(query, orgID, consumer, ERROR_CONSUMER, EMPTY_ACTION, false);

        return String.join("\n", rows);
    }

    @Override
    public void queryRaw(@Nonnull final String query,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onResponse, "onResponse");

        queryRaw(query, null, orgID, onResponse);
    }

    @Override
    public void queryRaw(@Nonnull final Query query,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse) {

        Arguments.checkNotNull(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onResponse, "onResponse");

        queryRaw(query, orgID, onResponse, ERROR_CONSUMER);
    }

    @Override
    public void queryRaw(@Nonnull final String query,
                         @Nullable final Dialect dialect,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onResponse, "onResponse");

        queryRaw(query, dialect, orgID, onResponse, ERROR_CONSUMER);
    }

    @Override
    public void queryRaw(@Nonnull final String query,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse,
                         @Nonnull final Consumer<? super Throwable> onError) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNotNull(onResponse, "onNext");
        Arguments.checkNotNull(onError, "onError");

        queryRaw(query, orgID, onResponse, onError, EMPTY_ACTION);
    }

    @Override
    public void queryRaw(@Nonnull final Query query,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse,
                         @Nonnull final Consumer<? super Throwable> onError) {

        queryRaw(query, orgID, onResponse, onError, EMPTY_ACTION);
    }

    @Override
    public void queryRaw(@Nonnull final String query,
                         @Nullable final Dialect dialect,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse,
                         @Nonnull final Consumer<? super Throwable> onError) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onResponse, "onNext");
        Arguments.checkNotNull(onError, "onError");

        queryRaw(query, dialect, orgID, onResponse, onError, EMPTY_ACTION);
    }

    @Override
    public void queryRaw(@Nonnull final String query,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse,
                         @Nonnull final Consumer<? super Throwable> onError,
                         @Nonnull final Runnable onComplete) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onResponse, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(onComplete, "onComplete");

        queryRaw(query, null, orgID, onResponse, onError, onComplete);
    }

    @Override
    public void queryRaw(@Nonnull final String query,
                         @Nullable final Dialect dialect,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse,
                         @Nonnull final Consumer<? super Throwable> onError,
                         @Nonnull final Runnable onComplete) {

        Arguments.checkNonEmpty(query, "query");
        Arguments.checkNonEmpty(orgID, "orgID");
        Arguments.checkNotNull(onResponse, "onNext");
        Arguments.checkNotNull(onError, "onError");
        Arguments.checkNotNull(onComplete, "onComplete");

        queryRaw(new Query().query(query).dialect(dialect), orgID, onResponse, onError, onComplete, true);
    }

    @Override
    public void queryRaw(@Nonnull final Query query,
                         @Nonnull final String orgID,
                         @Nonnull final BiConsumer<Cancellable, String> onResponse,
                         @Nonnull final Consumer<? super Throwable> onError,
                         @Nonnull final Runnable onComplete) {

        queryRaw(query, orgID, onResponse, onError, onComplete, true);
    }

    private void query(@Nonnull final Query query,
                       @Nonnull final String orgID,
                       @Nonnull final FluxCsvParser.FluxResponseConsumer responseConsumer,
                       @Nonnull final Consumer<? super Throwable> onError,
                       @Nonnull final Runnable onComplete,
                       @Nonnull final Boolean asynchronously) {

        Call<ResponseBody> queryCall = service
                .queryPostResponseBody(null, "text/csv", "application/json",
                        null, orgID, query);


        LOG.log(Level.FINEST, "Prepare query \"{0}\" with dialect \"{1}\" on organization \"{2}\".",
                new Object[]{query, query.getDialect(), orgID});

        query(queryCall, responseConsumer, onError, onComplete, asynchronously);
    }

    private void queryRaw(@Nonnull final Query query,
                          @Nonnull final String orgID,
                          @Nonnull final BiConsumer<Cancellable, String> onResponse,
                          @Nonnull final Consumer<? super Throwable> onError,
                          @Nonnull final Runnable onComplete,
                          @Nonnull final Boolean asynchronously) {

        Call<ResponseBody> queryCall = service
                .queryPostResponseBody(null, "text/csv",
                        "application/json", null, orgID, query);

        LOG.log(Level.FINEST, "Prepare raw query \"{0}\" with dialect \"{1}\" on organization \"{2}\".",
                new Object[]{query, query.getDialect(), orgID});

        queryRaw(queryCall, onResponse, onError, onComplete, asynchronously);
    }
}