package com.envest.services.components.recommendationengine;

import java.util.ArrayList;
import java.util.List;

import com.envest.services.components.util.Product.Product;
import com.envest.services.response.ProfileResponse;

public class RecommendationResponse {
		ProfileResponse profileResponse;
		
		List<Product> recommendedProducts= new ArrayList<Product>();
		
		public List<Product> getRecommendedProducts()
		{
			return recommendedProducts;
		}
		
		public void addRecommendedProduct(Product product)
		{
			recommendedProducts.add(product);
		}
		
		public ProfileResponse getProfile()
		{
			return profileResponse;
		}
		
		public void setProfile(ProfileResponse profileResponse)
		{
			this.profileResponse = profileResponse;
		}
		
		public void Merge(RecommendationResponse response)
		{
			if(response.profileResponse != null)
			{
				this.profileResponse = response.profileResponse;
			}
			
			this.recommendedProducts.addAll(response.recommendedProducts);
		}
}
