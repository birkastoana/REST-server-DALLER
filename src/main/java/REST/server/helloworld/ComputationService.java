package REST.server.helloworld;

import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/computationservice")
public class ComputationService {

    @GET
    @Path("/calculate")
    @Produces({MediaType.APPLICATION_JSON})
    public String calculate(@QueryParam("n1") String num1, @QueryParam("n2") String num2, @QueryParam("op") String operator) {
//preparing
        JSONObject json = new JSONObject();
        double n1;
        double n2;
//start try catch block
        try {
//            try to cast parameter -> if fails we'll get NumberFormatException
            n1 = Double.parseDouble(num1);
            n2 = Double.parseDouble(num2);
//            choose requested operation, if no operation fits, give back error witch description
            switch (operator) {
                case "add":
                    json.put("Result", n1 + n2);
                    break;
                case "divide":
                    double result =  n1 / n2;
                    if (Double.isInfinite(result)) {
                        throw new ArithmeticException("Division by 0");
                    } else if (Double.isNaN(result) ) {
                        throw new ArithmeticException("Division of 0 by 0");
                    }
                    json.put("Result", result);
                    break;
                case "subtract":
                    json.put("Result", n1 - n2);
                    break;
                case "multiply":
                    json.put("Result", n1 * n2);
                    break;
                default:
                    json.put("Error", "No valid Operator. Use 'devide', 'subtract', 'multiply' or 'add'");
                    break;
            }
        } catch (NumberFormatException e) {
            json.put("Error", "Error! Arguments are not numbers!");
        } catch (ArithmeticException e) {
            json.put("Error", "Arithmetic Exception: " + e.getMessage());
        } finally {
            return json.toJSONString();
        }

    }
}
