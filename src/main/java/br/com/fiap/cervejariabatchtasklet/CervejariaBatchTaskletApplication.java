//package br.com.fiap.cervejariabatchtasklet;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.io.File;
//import java.nio.file.Paths;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class CervejariaBatchTaskletApplication {
//
//	Logger logger = LoggerFactory.getLogger(CervejariaBatchTaskletApplication.class);
//
//	@Bean
//	public Tasklet deleteTasklet(@Value("${file.path}") String path){
//		return (contribution, chunkContext) -> {
//			File file = Paths.get(path).toFile();
//			if(file.delete()){
//				logger.info("Arquivo deletado");
//			}else{
//				logger.error("Não foi possível deletar o arquivo");
//			}
//			return RepeatStatus.FINISHED;
//		};
//	}
//
//	@Bean
//	@Qualifier("taskletstep")
//	public Step step(StepBuilderFactory stepBuilderFactory,
//					 Tasklet tasklet){
//		return stepBuilderFactory.get("Delete File Step")
//				.tasklet(tasklet)
//				.allowStartIfComplete(true)
//				.build();
//	}
//
//	@Bean
//	public Job job(JobBuilderFactory jobBuilderFactory,
//				   @Qualifier("taskletstep") Step step){
//		return jobBuilderFactory.get("Delete File Job")
//				.start(step)
//				.build();
//	}
//
//
//}
