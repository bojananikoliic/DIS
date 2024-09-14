package bojana.calculator;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/paymentCalculator")
public class MainController {
	@PostMapping(path="/newPaymentCalculation", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, Float> newPaymentCalculation (@RequestBody RiskCalculation newRiskCalculation) throws IOException, TimeoutException {
		System.out.println(newRiskCalculation);
		HashMap<String, Float> response = new HashMap<String, Float>();

		float payment = (newRiskCalculation.getAmount() * (1 + newRiskCalculation.getInterest() * newRiskCalculation.getYears())) / newRiskCalculation.getYears() * 12;
		
		response.put("payment", payment);
		
		return response;
	}
}
