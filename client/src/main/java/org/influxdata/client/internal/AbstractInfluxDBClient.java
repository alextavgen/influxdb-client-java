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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;

import org.influxdata.Arguments;
import org.influxdata.client.InfluxDBClientOptions;
import org.influxdata.client.JSON;
import org.influxdata.client.domain.Check;
import org.influxdata.client.domain.Dialect;
import org.influxdata.client.service.HealthService;
import org.influxdata.exceptions.InfluxException;
import org.influxdata.internal.AbstractRestClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Jakub Bednar (bednar@github) (20/11/2018 07:13)
 */
public abstract class AbstractInfluxDBClient extends AbstractRestClient {

    private static final Logger LOG = Logger.getLogger(AbstractInfluxDBClient.class.getName());

    public static final Dialect DEFAULT_DIALECT =  new Dialect().header(true)
            .delimiter(",")
            .commentPrefix("#")
            .addAnnotationsItem(Dialect.AnnotationsEnum.DATATYPE)
            .addAnnotationsItem(Dialect.AnnotationsEnum.GROUP).addAnnotationsItem(Dialect.AnnotationsEnum.DEFAULT);

    public final HealthService healthService;

    protected final Retrofit retrofit;

    protected final HttpLoggingInterceptor loggingInterceptor;
    protected final GzipInterceptor gzipInterceptor;
    private final AuthenticateInterceptor authenticateInterceptor;
    private final OkHttpClient okHttpClient;

    public AbstractInfluxDBClient(@Nonnull final InfluxDBClientOptions options) {

        Arguments.checkNotNull(options, "InfluxDBClientOptions");

        this.loggingInterceptor = new HttpLoggingInterceptor();
        this.loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        this.authenticateInterceptor = new AuthenticateInterceptor(options);
        this.gzipInterceptor = new GzipInterceptor();

        this.okHttpClient = options.getOkHttpClient()
                .addInterceptor(this.loggingInterceptor)
                .addInterceptor(this.authenticateInterceptor)
                .addInterceptor(this.gzipInterceptor)
                .build();

        this.authenticateInterceptor.initToken(okHttpClient);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(options.getUrl())
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new JSON().getGson()))
                .build();

        this.healthService = retrofit.create(HealthService.class);
    }

    public void close() {

        //
        // signout
        //
        try {
            this.authenticateInterceptor.signout();
        } catch (IOException e) {
            LOG.log(Level.FINEST, "The signout exception", e);
        }

        //
        // Shutdown OkHttp
        //
        okHttpClient.connectionPool().evictAll();
        okHttpClient.dispatcher().executorService().shutdown();
    }

    @Nonnull
    protected Check health(final Call<Check> healthCall) {

        Arguments.checkNotNull(healthCall, "health call");

        try {
            return execute(healthCall);
        } catch (InfluxException e) {
            Check health = new Check();
            health.setName("influxdb");
            health.setStatus(Check.StatusEnum.FAIL);
            health.setMessage(e.getMessage());

            return health;
        }
    }
}