package com.birt.api;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.Calendar;

@RestController
@SpringBootApplication
public class BirtReportApplication {

    @Value("${reports.relative.path}")
    private String reportsPath;
    @Value("${images.relative.path}")
    private String imagesPath;	
	@Value("${temp.files.path}")
	private String tempPath;      
    
    private ApplicationContext context;
	
	public static void main(String[] args) {
		SpringApplication.run(BirtReportApplication.class, args);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/report")
    public ResponseEntity<byte[]> generateReport(@RequestBody String json) {
        try {

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> parametersMap = objectMapper.readValue(json, Map.class);        	
        	
			EngineConfig config = new EngineConfig();
			config.getAppContext().put("spring", this.context);
			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			IReportEngine engine = factory.createReportEngine(config);

			IReportRunnable report = engine.openReportDesign(reportsPath + parametersMap.get("report"));

			IRunAndRenderTask task = engine.createRunAndRenderTask(report);
			for (Entry<String, String> parametro : parametersMap.entrySet()) {
				task.setParameterValue(parametro.getKey(), parametro.getValue());
			}

			String reportFileName = tempPath + Calendar.getInstance().getTimeInMillis() + ".pdf";
			
			PDFRenderOption options = new PDFRenderOption();
			options.setOutputFormat("pdf");
			options.setOutputFileName(reportFileName);
			task.setRenderOption(options);
			task.run();
			task.close();			

            byte[] relatorioBytes = Files.readAllBytes(Paths.get(reportFileName));

            Files.deleteIfExists(Paths.get(reportFileName));

            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .body(relatorioBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
