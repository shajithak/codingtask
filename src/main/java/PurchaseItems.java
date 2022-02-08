import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PurchaseItems {

	public List<Object> getPurchasedProducts(List<String> productCode, Map<String, Map<String, String>> mappings) {

		List<Object> purchasedProductList = new ArrayList<Object>();

		// Sort list to get unique items
		Collections.sort(productCode);

		System.out.println("List of products: " + productCode);
		System.out.println("Mappings: " + mappings);

		// map to store count values
		Map<String, Long> frequencyMap = productCode.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		TreeMap<String, Long> sorted = new TreeMap<>();
		sorted.putAll(frequencyMap);

		for (Map.Entry<String, Long> entry : sorted.entrySet()) {

			for (Map.Entry<String, Map<String, String>> value : mappings.entrySet()) {

				if (entry.getKey().equals(value.getKey())) {

					Map<String, Object> productMappings = new LinkedHashMap<String, Object>();

					productMappings.put("version", value.getValue().get("version"));

					if (value.getValue().get("edition") != null)
						productMappings.put("edition", value.getValue().get("edition"));

					productMappings.put("quantity", entry.getValue());

					purchasedProductList.add(productMappings);
				}

			}

		}

		System.out.println("Purchased items:");
		System.out.println(purchasedProductList);

		return purchasedProductList;
	}

	public static void main(String[] args) throws Exception {

		PurchaseItems product = new PurchaseItems();

		// Instantiate List
		List<String> productCode = Arrays.asList("CVCD", "SDFD", "DDDF", "SDFD");

		Map<String, Map<String, String>> mappings = new LinkedHashMap<String, Map<String, String>>();

		// Instantiate mappings
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("version", "1");
		map1.put("edition", "X");
		mappings.put("CVCD", map1);

		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("version", "2");
		map2.put("edition", "Z");
		mappings.put("SDFD", map2);

		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("version", "1");
		mappings.put("DDDF", map3);

		// call function
		product.getPurchasedProducts(productCode, mappings);

	}
}
