package com.quesito.webstore.controller;

import com.quesito.webstore.domain.Product;
import com.quesito.webstore.repository.ProductRepository;
import com.quesito.webstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/market")
public class ProductController {
    @Autowired
    public ProductService productService;
    @Autowired
    public ServletContext servletContext;


    @RequestMapping("/products")
    public String list(Model model){

        model.addAttribute("products",productService.getAllProducts());
        return  "products";
    }
    @RequestMapping("/update/stock")
    public String updateStock(Model model){
        productService.updateAllStock();
        return "redirect:/products";

    }
    @RequestMapping("/products/{category}")
    public String getProductsByCategory(Model model, @PathVariable("category") String category){
        model.addAttribute("products",productService.getProductsByCategory(category));
        return "products";
    }

    @RequestMapping("/products/filter/{params}")
    public String getProductsByFilter(@MatrixVariable(pathVar = "params") Map<String, List<String>> filterParams,Model model){
        model.addAttribute("products",productService.getProductsByFilter(filterParams));
        return "products";
    }
    @RequestMapping("/product")
    public String getProductById(Model model, @RequestParam("id") String id){
        model.addAttribute("product",productService.getProductById(id));
        return "product";
    }
    @RequestMapping(value = "/products/add",method = RequestMethod.GET)
    public String getAddNewProductForm(Model model){
        model.addAttribute("newProduct",new Product());
        return "addProduct";
    }

//    @RequestMapping(value = "/products/add",method = RequestMethod.POST )
//    public String processAddNewProductForm(@ModelAttribute("newProduct") Product product,
//                                           BindingResult result,
//                                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
//        System.out.println(multipartFile);
//        String[] suppressedFields = result.getSuppressedFields();
//        if (suppressedFields.length > 0) {
//            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
//        }
//
//
//        Files.copy(multipartFile.getInputStream(),
//                new File(servletContext.getRealPath("/WEB-INF/images/"),product.getProductId()+".jpg").toPath(),
//                StandardCopyOption.REPLACE_EXISTING);
//        System.out.println("**************");
//        productService.addProduct(product);
//        return  "redirect:/market/products";
//    }

//    @RequestMapping(value = "/products/add",method = RequestMethod.POST )
//    public String processAddNewProductForm(@ModelAttribute("newProduct") Product product,BindingResult result) throws IOException {
//        String[] suppressedFields = result.getSuppressedFields();
//        if (suppressedFields.length > 0) {
//            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
//        }
//
//        MultipartFile multipartFile=product.getProductImage();
//        String rootPath = servletContext.getRealPath("/");
//        String path = String.format("%sresources\\images", rootPath);
//        System.out.println(path);
//
//        Files.copy(multipartFile.getInputStream(),
//                new File(path,product.getProductId()+".png").toPath(),
//                StandardCopyOption.REPLACE_EXISTING);
//        System.out.println("**************");
//        productService.addProduct(product);
//        return  "redirect:/market/products";
//    }


    @RequestMapping(value = "/products/{id}/image",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProductImage(@PathVariable("id") String id) throws IOException {

        String path = String.format("/WEB-INF/images/%s.jpg",id);
        System.out.println(path);

        InputStream resourceAsStream = servletContext.getResourceAsStream(path);

        byte[] bytes =  IOUtils.toByteArray(resourceAsStream);
        System.out.println(bytes.length+" "+id);
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }


    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setAllowedFields("productId",
                "name",
                "unitPrice",
                "description",
                "manufacturer",
                "category",
                "unitsInStock",
                "condition",
                "productImage");

    }



}
