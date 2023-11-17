package com.guitar.shop;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

// Ajusta el puerto de tu frontend Angular si es necesario

@RestController
public class PagoController {
    @Value("${frontendUrl}")
    private String frontendUrl;

    // Constructor o algún otro método para configurar tu clave API de Stripe
    public PagoController() {
        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");
    }

    @PostMapping("/api/pagos/crear-sesion")
    public ResponseEntity<?> crearSesionStripe(@RequestBody List<Producto> productos) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (Producto producto : productos) {
            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("eur")
                                            .setUnitAmount(producto.getPrice())
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(producto.getProductName())
                                                            .build())
                                            .build())
                            .setQuantity(producto.getQuantity())
                            .build());
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setSuccessUrl(frontendUrl + "/payment-success")
                .setCancelUrl(frontendUrl + "/payment-fail")
                .addAllLineItem(lineItems).setMode(SessionCreateParams.Mode.PAYMENT).build();

        try

        {
            Session session = Session.create(params);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("sessionURL", session.getUrl());
            // responseData.put("sessionData", session.getId());
            // Imprimir en consola el valor de responseData
            System.out.println("responseData: " + responseData.toString());
            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la sesión de Stripe: " + e.getMessage());
        }
    }

}
