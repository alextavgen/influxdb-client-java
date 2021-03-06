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

/**
 * Optional default range of the Y-axis
 */
@ApiModel(description = "Optional default range of the Y-axis")

public class DashboardQueryRange {
  public static final String SERIALIZED_NAME_UPPER = "upper";
  @SerializedName(SERIALIZED_NAME_UPPER)
  private Long upper = null;

  public static final String SERIALIZED_NAME_LOWER = "lower";
  @SerializedName(SERIALIZED_NAME_LOWER)
  private Long lower = null;

  public DashboardQueryRange upper(Long upper) {
    this.upper = upper;
    return this;
  }

   /**
   * Upper bound of the display range of the Y-axis
   * @return upper
  **/
  @ApiModelProperty(value = "Upper bound of the display range of the Y-axis")
  public Long getUpper() {
    return upper;
  }

  public void setUpper(Long upper) {
    this.upper = upper;
  }

  public DashboardQueryRange lower(Long lower) {
    this.lower = lower;
    return this;
  }

   /**
   * Lower bound of the display range of the Y-axis
   * @return lower
  **/
  @ApiModelProperty(value = "Lower bound of the display range of the Y-axis")
  public Long getLower() {
    return lower;
  }

  public void setLower(Long lower) {
    this.lower = lower;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardQueryRange dashboardQueryRange = (DashboardQueryRange) o;
    return Objects.equals(this.upper, dashboardQueryRange.upper) &&
        Objects.equals(this.lower, dashboardQueryRange.lower);
  }

  @Override
  public int hashCode() {
    return Objects.hash(upper, lower);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardQueryRange {\n");
    sb.append("    upper: ").append(toIndentedString(upper)).append("\n");
    sb.append("    lower: ").append(toIndentedString(lower)).append("\n");
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

