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
import org.influxdata.client.domain.BadStatement;
import org.influxdata.client.domain.BuiltinStatement;
import org.influxdata.client.domain.Expression;
import org.influxdata.client.domain.ExpressionStatement;
import org.influxdata.client.domain.Identifier;
import org.influxdata.client.domain.MemberAssignment;
import org.influxdata.client.domain.MemberExpression;
import org.influxdata.client.domain.OptionStatement;
import org.influxdata.client.domain.ReturnStatement;
import org.influxdata.client.domain.TestStatement;
import org.influxdata.client.domain.VariableAssignment;

/**
 * Statement
 */

public class Statement {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Statement {\n");
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

