package eu.makke4ever.projects.word_combinations_api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTest
{

    private static final String FILE_ENDPOINT = "api/file";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void parseFile() throws Exception
    {
        final byte[] inputArray = "Test String".getBytes();
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "tempFileName",
                MediaType.TEXT_PLAIN_VALUE, inputArray);
        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(multipart(getFileEndpointUrl()).file(mockMultipartFile)).andExpect(status().isOk());
    }

    private String getFileEndpointUrl()
    {
        return getLocalServerUrl() + FILE_ENDPOINT;
    }

    private String getLocalServerUrl()
    {
        return String.format("http://localhost:%d/", this.port);
    }
}
