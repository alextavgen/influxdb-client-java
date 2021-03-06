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
package example;

import org.influxdata.LogLevel;
import org.influxdata.client.flux.FluxClient;
import org.influxdata.client.flux.FluxClientFactory;

@SuppressWarnings("CheckStyle")
public class FluxExample {

    public static void main(final String[] args) {

        FluxClient fluxClient = FluxClientFactory.create(
            "http://localhost:8086/").setLogLevel(LogLevel.BODY);

        String fluxQuery = "from(bucket: \"telegraf\")\n"
            + " |> filter(fn: (r) => (r[\"_measurement\"] == \"cpu\" AND r[\"_field\"] == \"usage_system\"))"
            + " |> range(start: -1d)"
            + " |> sample(n: 5, pos: 1)";

        fluxClient.query(
            fluxQuery, (cancellable, record) -> {
                // process the flux query records
                System.out.println(record.getTime() + ": " + record.getValue());

            }, error -> {
                // error handling while processing result
                error.printStackTrace();

            }, () -> {
                // on complete
                System.out.println("Query completed");
            });

    }
}
