package NeuroTech.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import services.CEPRepository;
import services.CEPService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		String cep = "89010025"; // CEP desejado
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://brasilapi.com.br/api/cep/v1/" + cep))
				.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				System.out.println("Resposta da API:");
				System.out.println(response.body());

				// Mapear a resposta JSON para a entidade CEP
				ObjectMapper objectMapper = new ObjectMapper();
				CEPService.CEP cepEntity = objectMapper.readValue(response.body(), CEPService.CEP.class);

				// Salvar os dados no banco de dados
				RestTemplate restTemplate = new RestTemplate(); // Exemplo, ajuste de acordo com a sua implementação
				CEPRepository cepRepository = new CEPRepository() {
					@Override
					public Optional<CEPService.CEP> findById(Long id) {
						return Optional.empty();
					}

					@Override
					public boolean existsById(Long aLong) {
						return false;
					}

					@Override
					public Iterable<CEPService.CEP> findAll() {
						return null;
					}

					@Override
					public Iterable<CEPService.CEP> findAllById(Iterable<Long> longs) {
						return null;
					}

					@Override
					public long count() {
						return 0;
					}

					@Override
					public <S extends CEPService.CEP> S save(S entity) {
						return null;
					}

					@Override
					public <S extends CEPService.CEP> Iterable<S> saveAll(Iterable<S> entities) {
						return null;
					}

					@Override
					public void deleteById(Long id) {

					}

					@Override
					public void delete(CEPService.CEP entity) {

					}

					@Override
					public void deleteAllById(Iterable<? extends Long> longs) {

					}

					@Override
					public void deleteAll(Iterable<? extends CEPService.CEP> entities) {

					}

					@Override
					public void deleteAll() {

					}

					@Override
					public <S extends CEPService.CEP> S update(S entity) {
						return null;
					}
				}; // Exemplo, ajuste de acordo com a sua implementação
				CEPService cepService = new CEPService(restTemplate, cepRepository);

				cepService.saveCEPInformation(cepEntity);
			} else {
				System.out.println("Erro ao fazer a solicitação à API. Código de status: " + response.statusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}