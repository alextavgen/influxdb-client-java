/*
 * Influx API Service
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.influxdata.client.domain;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import org.influxdata.client.domain.DashboardQueryRange;
import org.influxdata.client.domain.QueryConfig;

/**
 * DashboardQuery
 */

public class DashboardQuery {
  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label = null;

  public static final String SERIALIZED_NAME_RANGE = "range";
  @SerializedName(SERIALIZED_NAME_RANGE)
  private DashboardQueryRange range = null;

  public static final String SERIALIZED_NAME_QUERY = "query";
  @SerializedName(SERIALIZED_NAME_QUERY)
  private String query = null;

  public static final String SERIALIZED_NAME_SOURCE = "source";
  @SerializedName(SERIALIZED_NAME_SOURCE)
  private String source = null;

  public static final String SERIALIZED_NAME_QUERY_CONFIG = "queryConfig";
  @SerializedName(SERIALIZED_NAME_QUERY_CONFIG)
  private QueryConfig queryConfig = null;

  public DashboardQuery label(String label) {
    this.label = label;
    return this;
  }

   /**
   * Optional Y-axis user-facing label
   * @return label
  **/
  @ApiModelProperty(value = "Optional Y-axis user-facing label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public DashboardQuery range(DashboardQueryRange range) {
    this.range = range;
    return this;
  }

   /**
   * Get range
   * @return range
  **/
  @ApiModelProperty(value = "")
  public DashboardQueryRange getRange() {
    return range;
  }

  public void setRange(DashboardQueryRange range) {
    this.range = range;
  }

  public DashboardQuery query(String query) {
    this.query = query;
    return this;
  }

   /**
   * Get query
   * @return query
  **/
  @ApiModelProperty(required = true, value = "")
  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public DashboardQuery source(String source) {
    this.source = source;
    return this;
  }

   /**
   * Optional URI for data source for this query
   * @return source
  **/
  @ApiModelProperty(value = "Optional URI for data source for this query")
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public DashboardQuery queryConfig(QueryConfig queryConfig) {
    this.queryConfig = queryConfig;
    return this;
  }

   /**
   * Get queryConfig
   * @return queryConfig
  **/
  @ApiModelProperty(value = "")
  public QueryConfig getQueryConfig() {
    return queryConfig;
  }

  public void setQueryConfig(QueryConfig queryConfig) {
    this.queryConfig = queryConfig;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardQuery dashboardQuery = (DashboardQuery) o;
    return Objects.equals(this.label, dashboardQuery.label) &&
        Objects.equals(this.range, dashboardQuery.range) &&
        Objects.equals(this.query, dashboardQuery.query) &&
        Objects.equals(this.source, dashboardQuery.source) &&
        Objects.equals(this.queryConfig, dashboardQuery.queryConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, range, query, source, queryConfig);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardQuery {\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    range: ").append(toIndentedString(range)).append("\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    queryConfig: ").append(toIndentedString(queryConfig)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

