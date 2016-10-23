package com.example.sic.myrestfulclienttest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Rest(rootUrl = "http://192.168.1.101:8080/restful_test/webapi/product", converters = MappingJackson2HttpMessageConverter.class)
public interface ProductsApi {

    @Get("")
    List<Product> getProducts();

    @Put("")
    void updateProduct(@Body Product product);

    @Delete("/{productId}")
    @Header(name = "Content-Type", value = "application/json")
    void deleteProduct(@Path String productId);

    @Post("")
    void addProduct(@Body Product product);
}
