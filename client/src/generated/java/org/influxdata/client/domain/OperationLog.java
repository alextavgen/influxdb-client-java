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
import java.time.OffsetDateTime;
import org.influxdata.client.domain.OperationLogLinks;

/**
 * OperationLog
 */

public class OperationLog {
  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description = null;

  public static final String SERIALIZED_NAME_TIME = "time";
  @SerializedName(SERIALIZED_NAME_TIME)
  private OffsetDateTime time = null;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private OperationLogLinks links = null;

  public OperationLog description(String description) {
    this.description = description;
    return this;
  }

   /**
   * A description of the event that occurred.
   * @return description
  **/
  @ApiModelProperty(example = "Bucket Created", value = "A description of the event that occurred.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OperationLog time(OffsetDateTime time) {
    this.time = time;
    return this;
  }

   /**
   * Time event occurred, RFC3339Nano.
   * @return time
  **/
  @ApiModelProperty(value = "Time event occurred, RFC3339Nano.")
  public OffsetDateTime getTime() {
    return time;
  }

  public void setTime(OffsetDateTime time) {
    this.time = time;
  }

  public OperationLog links(OperationLogLinks links) {
    this.links = links;
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public OperationLogLinks getLinks() {
    return links;
  }

  public void setLinks(OperationLogLinks links) {
    this.links = links;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationLog operationLog = (OperationLog) o;
    return Objects.equals(this.description, operationLog.description) &&
        Objects.equals(this.time, operationLog.time) &&
        Objects.equals(this.links, operationLog.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, time, links);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationLog {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

