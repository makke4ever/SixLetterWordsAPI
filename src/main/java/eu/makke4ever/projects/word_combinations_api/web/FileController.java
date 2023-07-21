package eu.makke4ever.projects.word_combinations_api.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eu.makke4ever.projects.word_combinations_api.service.WordCombinationsService;

@RestController
@RequestMapping("/api")
public class FileController
{
    private WordCombinationsService wordCombinationsService;

    @Value("${combination.word.size}")
    private int combinationWordSize;

    private static final String LINE_ENDING = "\n";

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    public FileController(final WordCombinationsService wordCombinationsService)
    {
        this.wordCombinationsService = wordCombinationsService;
    }

    @PostMapping(path = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> doPostFile(@RequestParam("file") final MultipartFile file)
    {
        LOGGER.debug("Start posting file");
        List<String> allWords;
        try
        {
            allWords = parseLinesFromFile(file);
            if (LOGGER.isTraceEnabled())
            {
                LOGGER.trace("Looking for combinations of size {} in:\n{}",
                             this.combinationWordSize,
                             allWords.stream().collect(Collectors.joining(LINE_ENDING)));
            }
        }
        catch (final IOException e)
        {
            LOGGER.error("Parsing of the input file failed.", e);
            return new ResponseEntity<>("Parsing of the input file failed. See server log for more details.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final List<String> result = new ArrayList<>();
        this.wordCombinationsService.findCombinations(allWords, this.combinationWordSize, result::add);
        LOGGER.debug("End posting file");
        return new ResponseEntity<>(result.stream().collect(Collectors.joining(LINE_ENDING)), HttpStatus.OK);
    }

    private List<String> parseLinesFromFile(final MultipartFile file) throws IOException
    {
        final List<String> result = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream())
        {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    result.add(line);
                }
            }
        }
        return result;
    }
}
