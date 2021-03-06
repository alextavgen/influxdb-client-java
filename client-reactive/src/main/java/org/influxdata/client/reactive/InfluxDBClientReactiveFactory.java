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
package org.influxdata.client.reactive;

import javax.annotation.Nonnull;

import org.influxdata.Arguments;
import org.influxdata.client.InfluxDBClientOptions;
import org.influxdata.client.reactive.internal.InfluxDBClientReactiveImpl;

/**
 * The Factory that create a instance of a InfluxDB reactive client.
 *
 * @author Jakub Bednar (bednar@github) (20/11/2018 07:09)
 */
public final class InfluxDBClientReactiveFactory {

    private InfluxDBClientReactiveFactory() {
    }

    /**
     * Create a instance of the InfluxDB 2.0 reactive client.
     *
     * @param url the url to connect to the InfluxDB
     * @return client
     * @see InfluxDBClientOptions.Builder#url(String)
     */
    @Nonnull
    public static InfluxDBClientReactive create(@Nonnull final String url) {

        InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                .url(url)
                .build();

        return create(options);
    }

    /**
     * Create a instance of the InfluxDB 2.0 reactive client.
     *
     * @param url      the url to connect to the InfluxDB
     * @param username the username to use in the basic auth
     * @param password the password to use in the basic auth
     * @return client
     * @see InfluxDBClientOptions.Builder#url(String)
     */
    @Nonnull
    public static InfluxDBClientReactive create(@Nonnull final String url,
                                                @Nonnull final String username,
                                                @Nonnull final char[] password) {

        InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                .url(url)
                .authenticate(username, password)
                .build();

        return create(options);
    }

    /**
     * Create a instance of the InfluxDB 2.0 reactive client.
     *
     * @param url   the url to connect to the InfluxDB
     * @param token the token to use for the authorization
     * @return client
     * @see InfluxDBClientOptions.Builder#url(String)
     */
    @Nonnull
    public static InfluxDBClientReactive create(@Nonnull final String url, @Nonnull final char[] token) {

        InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                .url(url)
                .authenticateToken(token)
                .build();

        return create(options);
    }

    /**
     * Create a instance of the InfluxDB 2.0 reactive client.
     *
     * @param options the connection configuration
     * @return client
     */
    @Nonnull
    public static InfluxDBClientReactive create(@Nonnull final InfluxDBClientOptions options) {

        Arguments.checkNotNull(options, "InfluxDBClientOptions");

        return new InfluxDBClientReactiveImpl(options);
    }
}