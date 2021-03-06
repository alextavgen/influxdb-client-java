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
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.influxdata.client.domain.Statement;

/**
 * a set of statements
 */
@ApiModel(description = "a set of statements")

public class Block extends Node {
  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type = null;

  public static final String SERIALIZED_NAME_BODY = "body";
  @SerializedName(SERIALIZED_NAME_BODY)
  @JsonAdapter(BlockBodyAdapter.class)
  private List<Statement> body = new ArrayList<>();

  public Block type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Block body(List<Statement> body) {
    this.body = body;
    return this;
  }

  public Block addBodyItem(Statement bodyItem) {
    if (this.body == null) {
      this.body = new ArrayList<>();
    }
    this.body.add(bodyItem);
    return this;
  }

   /**
   * block body
   * @return body
  **/
  @ApiModelProperty(value = "block body")
  public List<Statement> getBody() {
    return body;
  }

  public void setBody(List<Statement> body) {
    this.body = body;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Block block = (Block) o;
    return Objects.equals(this.type, block.type) &&
        Objects.equals(this.body, block.body) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, body, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Block {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
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

  public class BlockBodyAdapter implements JsonDeserializer<Object>, JsonSerializer<Object> {
    private final String discriminator = "type";

    public BlockBodyAdapter() {
    }

    @Override
    public Object deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {

      List<Object> results = new ArrayList<>();

      for (JsonElement arrayItem: json.getAsJsonArray()){
        JsonObject jsonObject = arrayItem.getAsJsonObject();

        String type = jsonObject.get(discriminator).getAsString();

        results.add(deserialize(type, jsonObject, context));
      }

      return results;
    }

    @Override
    public JsonElement serialize(Object object, Type typeOfSrc, JsonSerializationContext context) {

      return context.serialize(object);
    }

    private Object deserialize(final String type, final JsonElement json, final JsonDeserializationContext context) {

      if ("BadStatement".equals(type)) {
        return context.deserialize(json, BadStatement.class);
      }
      if ("VariableAssignment".equals(type)) {
        return context.deserialize(json, VariableAssignment.class);
      }
      if ("MemberAssignment".equals(type)) {
        return context.deserialize(json, MemberAssignment.class);
      }
      if ("ExpressionStatement".equals(type)) {
        return context.deserialize(json, ExpressionStatement.class);
      }
      if ("ReturnStatement".equals(type)) {
        return context.deserialize(json, ReturnStatement.class);
      }
      if ("OptionStatement".equals(type)) {
        return context.deserialize(json, OptionStatement.class);
      }
      if ("BuiltinStatement".equals(type)) {
        return context.deserialize(json, BuiltinStatement.class);
      }
      if ("TestStatement".equals(type)) {
        return context.deserialize(json, TestStatement.class);
      }

      return context.deserialize(json, Object.class);
    }
  }
}

