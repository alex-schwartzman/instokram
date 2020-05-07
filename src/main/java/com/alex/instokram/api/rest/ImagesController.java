package com.alex.instokram.api.rest;

import com.alex.instokram.domain.RestErrorInfo;
import com.alex.instokram.domain.UserUploadedImage;
import com.alex.instokram.exception.DataFormatException;
import com.alex.instokram.exception.ResourceNotFoundException;
import com.alex.instokram.service.ImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "/instokram/v1/image")
@Api(tags = {"images"})
public class ImagesController implements ApplicationEventPublisherAware {
    protected ApplicationEventPublisher eventPublisher;

    @Autowired
    private ImagesService imageService;

    protected static final String DEFAULT_PAGE_SIZE = "100";
    protected static final String DEFAULT_PAGE_NUM = "0";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    public
    @ResponseBody
    RestErrorInfo handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        return new RestErrorInfo(ex, "Please, check the request structure.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public
    @ResponseBody
    RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        return new RestErrorInfo(ex, "No such image.");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    @ApiOperation(value = "Get a paginated list of all images.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    Page<UserUploadedImage> getAllImages(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUM) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
            HttpServletRequest request, HttpServletResponse response) {
        return this.imageService.getAllImages(page, size);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register uploaded image.", notes = "After you have uploaded the image to the blob, pass it's url here.")
    public void uploadImage(@RequestBody UserUploadedImage image,
                            HttpServletRequest request, HttpServletResponse response) {

        UserUploadedImage uploadedImage = this.imageService.uploadImage(image);
        response.setHeader(HttpHeaders.LOCATION, request.getRequestURL().append("/").append(uploadedImage.getId()).toString());
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    @ApiOperation(value = "Get a single image.", notes = "You have to provide a valid image ID.")
    UserUploadedImage getImage(@PathVariable("id") Long id,
                               HttpServletRequest request, HttpServletResponse response) throws ResourceNotFoundException {
        Optional<UserUploadedImage> image = this.imageService.getImage(id);
        if (image.isPresent()) {
            return image.get();
        }
        throw new ResourceNotFoundException("image not found");
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an image.", notes = "You have to provide a valid image ID in the URL. Once deleted it can not be recovered.")
    public void deleteImage(@PathVariable("id") Long id, HttpServletRequest request,
                            HttpServletResponse response) {
        this.imageService.deleteImage(id);
    }
}
