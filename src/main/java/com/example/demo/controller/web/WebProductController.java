package com.example.demo.controller.web;

import com.example.demo.dto.product.ProductReqDto;
import com.example.demo.dto.product.ProductSearchDto;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Controller
@RequestMapping("/products")
public class WebProductController {

  private final ProductService productService;
  private final CategoryService categoryService;

  @GetMapping
  public String products(ProductSearchDto paramsDto, Model model) {
    model.addAttribute("products", productService.getProducts(paramsDto));
    return "products";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable UUID id) {
    productService.deleteProduct(id);
    return "redirect:/products";
  }

  @GetMapping("/add")
  public String addProduct(Model model) {
    model.addAttribute("productAddForm", new ProductReqDto());
    model.addAttribute("categories", categoryService.getCategories());
    return "product-add-form";
  }

  @PostMapping("/add")
  public String addProductForm(
    @Valid @ModelAttribute("productAddForm") ProductReqDto productDto,
    BindingResult bindingResult,
    Model model
  ) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("productAddForm", productDto);
      model.addAttribute("categories", categoryService.getCategories());
      return "product-add-form";
    }

    try {
      productService.createProduct(productDto);
      return "redirect:/products";
    } catch (Exception e) {
      model.addAttribute("productAddForm", productDto);
      model.addAttribute("categories", categoryService.getCategories());
      return "product-add-form";
    }
  }

  @GetMapping("/edit/{id}")
  public String editProduct(@PathVariable UUID id, Model model) {
    model.addAttribute("productEditForm", productService.getProduct(id));
    model.addAttribute("allCategories", categoryService.getCategories());
    model.addAttribute("id", id);
    return "product-edit-form";
  }

  @PostMapping("/edit/{id}")
  public String editProductForm(
    @PathVariable UUID id,
    @Valid @ModelAttribute("productEditForm") ProductReqDto productDto,
    BindingResult bindingResult,
    Model model
  ) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("productAddForm", productDto);
      model.addAttribute("allCategories", categoryService.getCategories());
      model.addAttribute("id", id);
      return "product-edit-form";
    }

    try {
      productService.updateProduct(id, productDto);
      return "redirect:/products";
    } catch (Exception e) {
      model.addAttribute("productEditForm", productDto);
      model.addAttribute("allCategories", categoryService.getCategories());
      model.addAttribute("id", id);
      return "product-edit-form";
    }
  }
}
