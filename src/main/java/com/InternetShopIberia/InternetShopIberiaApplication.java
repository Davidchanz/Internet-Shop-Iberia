package com.InternetShopIberia;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.model.*;
import com.InternetShopIberia.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class InternetShopIberiaApplication {
	public static void main(String[] args) {
		//org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();
		var context = SpringApplication.run(InternetShopIberiaApplication.class, args);
		addCategoryTemplate(context);
		addProductsTemplate(context);
		addUser(context);
		//192.168.100.10
	}

	private static void addUser(ConfigurableApplicationContext context) {
		var userService = context.getBean(UserService.class);
		var cartService = context.getBean(CartService.class);

		UserDto user = new UserDto();
		user.setUserName("Admin");
		user.setEmail("admin@mail.com");
		user.setFirstName("Admin");
		user.setLastName("Admin");
		user.setPassword("Admin");
		user.setMatchingPassword("Admin");
		userService.registerNewUserAccount(user);

		UserDto user2 = new UserDto();
		user2.setUserName("GN");
		user2.setEmail("GN@mail.com");
		user2.setFirstName("Giorgi");
		user2.setLastName("Nodia");
		user2.setPassword("123");
		user2.setMatchingPassword("123");
		var registered = userService.registerNewUserAccount(user2);

		Cart cart = new Cart();
		cart.setUser(registered);
		cart.setProducts(new ArrayList<>());
		cartService.addCart(cart);
	}

	private static void addProductsTemplate(ConfigurableApplicationContext context) {
		var productService = context.getBean(ProductService.class);
		var productDetailService = context.getBean(ProductDetailService.class);
		var categoryService = context.getBean(CategoryService.class);
		var productImageService = context.getBean(ProductImageService.class);
		var category = categoryService.findCategoryById(1L);
		var categoryA = categoryService.findCategoryById(3L);

		for(int i = 0; i < 5; i++) {
			Product product = new Product();
			product.setName("Acer Predator Helios 300 PH315-54-760S Gaming Laptop");
			product.setCategory(category);
			product.setDescription("Acer Predator Helios 300 PH315-54-760S Gaming Laptop | Intel i7-11800H | NVIDIA GeForce RTX 3060 GPU | 15.6\" FHD 144Hz 3ms IPS Display | 16GB DDR4 | 512GB SSD | Killer WiFi 6 | RGB Keyboard");
			product.setPrice(new BigDecimal("2199.00"));
			product.setPId(i+4804708L);

			ProductImage mPI = new ProductImage();
			mPI.setPath("71AGOX9MORL._AC_SX466_.jpg");
			productImageService.addProductImage(mPI);
			product.setMainImage(mPI);

			ProductDetail pd = new ProductDetail("Screen", "15\"");
			productDetailService.addProductDetail(pd);
			ProductDetail pd1 = new ProductDetail("Display", "FHD 144Hz 3ms IPS");
			productDetailService.addProductDetail(pd1);
			ProductDetail pd2 = new ProductDetail("CPU", "Intel i7-11800H");
			productDetailService.addProductDetail(pd2);
			ProductDetail pd3 = new ProductDetail("GPU", "NVIDIA GeForce RTX 3060");
			productDetailService.addProductDetail(pd3);
			ProductDetail pd4 = new ProductDetail("RAM", "16GB DDR4");
			productDetailService.addProductDetail(pd4);
			product.setDetails(List.of(pd, pd1, pd2, pd3, pd4));

			List<ProductImage> pIL = new ArrayList<>();
			for(int j = 1; j <= 5; j++){
				ProductImage pI = new ProductImage();
				pI.setPath(j+".jpg");
				productImageService.addProductImage(pI);
				pIL.add(pI);
			}
			product.setAllImages(pIL);

			productService.addProduct(product);
		}

		Product productN = new Product();
		productN.setName("Asus VivoBook 15S");
		productN.setCategory(categoryA);
		productN.setDescription("Asus VivoBook 15S | Intel i5-17700K | NVIDIA GeForce RTX 2070 GPU | 13.0\" FHD 60Hz 15ms IPS Display | 8GB DDR4 | 512GB SSD | Killer WiFi 6");
		productN.setPrice(new BigDecimal("56999.00"));
		productN.setPId(1233123L);
		ProductDetail pdN = new ProductDetail("Screen", "13\"");
		productDetailService.addProductDetail(pdN);
		productN.setDetails(List.of(pdN));

		List<ProductImage> pIL = new ArrayList<>();
		for(int j = 1; j <= 3; j++){
			ProductImage pI = new ProductImage();
			pI.setPath(j+".jpg");
			productImageService.addProductImage(pI);
			pIL.add(pI);
		}
		productN.setAllImages(pIL);

		productService.addProduct(productN);
	}

	private static void addCategoryTemplate(ConfigurableApplicationContext context){
		var categoryService = context.getBean(CategoryService.class);
		var Acer = new Category("Acer", "", new ArrayList<>());
		categoryService.addCategory(Acer);
		var VivoBook = new Category("VivoBook", "", new ArrayList<>());
		categoryService.addCategory(VivoBook);
		var Asus = new Category("Asus", "", List.of(
				VivoBook
		));
		categoryService.addCategory(Asus);
		var Laptops = new Category("Laptop", "", List.of(
				Asus,
				Acer
		));
		categoryService.addCategory(Laptops);
		var Android =  new Category("Android", "", new ArrayList<>());
		categoryService.addCategory(Android);
		var Apple = new Category("Apple", "", new ArrayList<>());
		categoryService.addCategory(Apple);
		var Phones = new Category("Phones", "", List.of(
				Apple,
				Android
		));
		categoryService.addCategory(Phones);

		Category categories = new Category("Categories", "", List.of(
				Phones,
				Laptops
		));
		categoryService.addCategory(categories);
	}

}
