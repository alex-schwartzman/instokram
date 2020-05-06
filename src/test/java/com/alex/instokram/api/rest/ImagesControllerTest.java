package com.alex.instokram.api.rest;

import com.alex.instokram.InstokramApplication;
import com.alex.instokram.domain.User;
import com.alex.instokram.domain.UserUploadedImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InstokramApplication.class)
class ImagesControllerTest {

    final static String IMAGES_API_ENDPOINT = "/instokram/v1/image";
    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost" + IMAGES_API_ENDPOINT + "/[0-9]+";
    private static final User user = new User();

    private MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        user.setAbout("About: Hello World User");
        user.setName("Mr Name Surname");
    }

    @Test
    void getAllImages() {
    }

    @Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get(IMAGES_API_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(0)));
    }


    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        UserUploadedImage r1 = mockImage("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        //CREATE
        MvcResult result = mvc.perform(post(IMAGES_API_ENDPOINT)
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE
        mvc.perform(get(IMAGES_API_ENDPOINT + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.blobUri", is(r1.getBlobUri())))
                .andExpect(jsonPath("$.user.name", is(r1.getUser().getName())))
                .andExpect(jsonPath("$.user.about", is(r1.getUser().getAbout())))
                .andExpect(jsonPath("$.description", is(r1.getDescription())));

        //DELETE
        mvc.perform(delete(IMAGES_API_ENDPOINT + "/" + id))
                .andExpect(status().isNoContent());

        //RETRIEVE should fail
        mvc.perform(get(IMAGES_API_ENDPOINT + "/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    private UserUploadedImage mockImage(String imagePrefix) {
        UserUploadedImage image = new UserUploadedImage();
        image.setBlobUri(imagePrefix + "_blobURI");
        image.setDescription(imagePrefix + "_description");
        image.setUser(user);
        return image;
    }

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }

    private byte[] toJson(Object r) throws Exception {
        return toJsonString(r).getBytes();
    }

    private String toJsonString(Object r) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(r);
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return result -> {
            Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
            assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
        };
    }

}