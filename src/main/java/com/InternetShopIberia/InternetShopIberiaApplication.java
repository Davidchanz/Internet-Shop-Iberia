package com.InternetShopIberia;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.model.*;
import com.InternetShopIberia.service.*;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableScheduling
public class InternetShopIberiaApplication {
	public static void main(String[] args) {
		//org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();
		var context = SpringApplication.run(InternetShopIberiaApplication.class, args);
		addCategoryTemplate(context);
		//addProductsTemplate(context);
		addProducts(context, "data/images/images_url.text", "data/products/laptops");
		addProducts(context, "data/images/phones.text", "data/products/phones");
		addProducts(context, "data/images/desktop.text", "data/products/desktop");
		addProducts(context, "data/images/monitors.text", "data/products/monitors");
		addProducts(context, "data/images/tablets.text", "data/products/tablets");
		addProducts(context, "data/images/smart-tv.text", "data/products/smart-tv");
		addProducts(context, "data/images/standard-tv.text", "data/products/standard-tv");
		addProducts(context, "data/images/headphones.text", "data/products/headphones");
		addProducts(context, "data/images/speakers.text", "data/products/speakers");
		addProducts(context, "data/images/refrigerators.text", "data/products/refrigerators");
		addProducts(context, "data/images/microwave.text", "data/products/microwave");
		addUser(context);
		//192.168.100.10
	}

	private static void addProducts(ConfigurableApplicationContext context, String imageUrlsFilePath, String prosuctFilePath) {
		var productService = context.getBean(ProductService.class);
		var productDetailService = context.getBean(ProductDetailService.class);
		var productImageService = context.getBean(ProductImageService.class);
		var categoryService = context.getBean(CategoryService.class);

		TreeMap<String, String> images = new TreeMap<>();
		try {
			Scanner sc = new Scanner(new File(imageUrlsFilePath));
			sc.useDelimiter(", ");
			while (sc.hasNext()){
				String url_name = sc.next();
				//System.out.println(url_name);
				String path = url_name.split(" \\| ")[0];
				String name = url_name.split(" \\| ")[1];
				name = name.substring(name.indexOf("_")+1, name.indexOf("."));
				images.put(name, path);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		File data = new File(prosuctFilePath);
		for(var file: data.listFiles()){
			String str = "";
			try {
				Scanner sc = new Scanner(file);
				while (sc.hasNext()){
					str += sc.nextLine();
				}
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			JSONObject jsonFile = new JSONObject(str);

			Product product = new Product();
			product.setName(jsonFile.getString("name"));
			product.setCategory(categoryService.findCategoryTitleLike(jsonFile.getString("category")).get(0));
			var price = jsonFile.getBigDecimal("origPrice").toString().replace('.', ',').split(",");
			product.setOrigPrice(new BigDecimal(Arrays.stream(price).reduce((s, s2) -> {
				return s+s2;
			}).get()));
			product.setPId(jsonFile.getLong("PId"));
			product.setOrigAbout(jsonFile.getString("origAbout"));

			ProductImage mPI = new ProductImage();
			mPI.setPath(images.get(jsonFile.getJSONObject("mainImage").getString("path")));
			productImageService.addProductImage(mPI);
			product.setMainImage(mPI);

			ArrayList<ProductDetail> details = new ArrayList<>();
			jsonFile.getJSONArray("details").forEach(detailJSON -> {
				String name = ((JSONObject)detailJSON).getString("name");
				String value = ((JSONObject)detailJSON).getString("value");
				ProductDetail pd = new ProductDetail(name, value);
				productDetailService.addProductDetail(pd);
				details.add(pd);
			});
			product.setDetails(details);

			List<ProductImage> pIL = new ArrayList<>();
			jsonFile.getJSONArray("allImages").forEach(imageJSON -> {
				String name = ((JSONObject)imageJSON).getString("path");
				ProductImage pI = new ProductImage();
				pI.setPath(images.get(name));
				productImageService.addProductImage(pI);
				pIL.add(pI);
			});
			product.setAllImages(pIL);

			productService.addProduct(product);
		}
	}

	private static void addUser(ConfigurableApplicationContext context) {
		var userService = context.getBean(UserService.class);
		var cartService = context.getBean(CartService.class);

		/*UserDto user = new UserDto();
		user.setUserName("Admin");
		user.setEmail("admin@mail.com");
		user.setFirstName("Admin");
		user.setLastName("Admin");
		user.setPassword("Admin");
		user.setMatchingPassword("Admin");
		userService.registerNewUserAccount(user);*/

		UserDto user2 = new UserDto();
		user2.setUserName("GN");
		user2.setEmail("tovliskatsi@gmail.com");
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
		var productImageService = context.getBean(ProductImageService.class);
		var categoryService = context.getBean(CategoryService.class);
		var category = categoryService.findCategoryById(1L);
		var categoryA = categoryService.findCategoryById(3L);

		addProduct(context, "Acer Predator Helios 300 PH315-54-760S Gaming Laptop",
				new BigDecimal(1000),
				category,
				1L);
		addProduct(context, "MSI Bit100",
				new BigDecimal(6000),
				category,
				2L);
		addProduct(context, "Apple MacBook 13",
				new BigDecimal(4000),
				category,
				3L);
		addProduct(context, "Microsoft Swift",
				new BigDecimal(5000),
				category,
				5L);
		addProduct(context, "UralCali M700",
				new BigDecimal(2000),
				category,
				4L);
		addProduct(context, "Dell Turbo 5",
				new BigDecimal(3000),
				category,
				6L);

		Product productN = new Product();
		productN.setName("Asus VivoBook 15S");
		productN.setCategory(categoryA);
		productN.setOrigPrice(new BigDecimal("56999.00"));
		productN.setPId(1233123L);
		productN.setOrigAbout("SUPERCHARGED RTX GRAPHICS - Gameplay graphics are silky smooth with the NVIDIA GeForce RTX 3060 6GB GDDR6 at 1050W with Dynamic Boost, with cutting-edge AI features like NVIDIA DLSS and Ray-Tracing\n" +
				"MUX SWITCH BOOST - A MUX Switch lets the GPU communicate directly with the display, increasing performance and decreasing latency");
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

	private static void addProduct(ConfigurableApplicationContext context, String name, BigDecimal price, Category category, Long pId){
		var productService = context.getBean(ProductService.class);
		var productDetailService = context.getBean(ProductDetailService.class);
		var productImageService = context.getBean(ProductImageService.class);

		Product product = new Product();
		product.setName(name);
		product.setCategory(category);
		product.setOrigPrice(price);
		product.setPId(pId);
		product.setOrigAbout("SUPERCHARGED RTX GRAPHICS - Gameplay graphics are silky smooth with the NVIDIA GeForce RTX 3060 6GB GDDR6 at 1050W with Dynamic Boost, with cutting-edge AI features like NVIDIA DLSS and Ray-Tracing\n" +
				"MUX SWITCH BOOST - A MUX Switch lets the GPU communicate directly with the display, increasing performance and decreasing latency");

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

	private static void addCategoryTemplate(ConfigurableApplicationContext context){
		var categoryService = context.getBean(CategoryService.class);
		var Laptops = new Category("Laptops", "", new ArrayList<>());
		categoryService.addCategory(Laptops);
		var Monitors = new Category("Monitors", "", new ArrayList<>());
		categoryService.addCategory(Monitors);
		var Desktop = new Category("Desktop", "", new ArrayList<>());
		categoryService.addCategory(Desktop);
		var PC = new Category("PC", "", List.of(
				Desktop,
				Laptops,
				Monitors
		));
		categoryService.addCategory(PC);
		var Phones = new Category("Phones", "", new ArrayList<>());
		categoryService.addCategory(Phones);
		var Tablets = new Category("Tablets", "", new ArrayList<>());
		categoryService.addCategory(Tablets);
		var MobilDevices = new Category("MobilDevices", "", List.of(
				Phones,
				Tablets
		));
		categoryService.addCategory(MobilDevices);
		var SmartTV = new Category("Smart-TV", "", new ArrayList<>());
		categoryService.addCategory(SmartTV);
		var StandardTV = new Category("Standard-TV", "", new ArrayList<>());
		categoryService.addCategory(StandardTV);
		var TV = new Category("TV", "", List.of(
				SmartTV,
				StandardTV
		));
		categoryService.addCategory(TV);

		var Headphones = new Category("Headphones", "", new ArrayList<>());
		categoryService.addCategory(Headphones);
		var Speakers = new Category("Speakers", "", new ArrayList<>());
		categoryService.addCategory(Speakers);
		var AudioDevices = new Category("AudioDevices", "", List.of(
				Headphones,
				Speakers
		));
		categoryService.addCategory(AudioDevices);

		var Refrigerators = new Category("Refrigerators", "", new ArrayList<>());
		categoryService.addCategory(Refrigerators);
		var Microwave = new Category("Microwave", "", new ArrayList<>());
		categoryService.addCategory(Microwave);
		var Home = new Category("Home", "", List.of(
				Refrigerators,
				Microwave
		));
		categoryService.addCategory(Home);

		Category categories = new Category("Categories", "", List.of(
				MobilDevices,
				PC,
				TV,
				AudioDevices,
				Home
		));
		categoryService.addCategory(categories);
	}

}
