package com.hoover.functionaltests;

import static org.jbehave.core.reporters.Format.CONSOLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.Steps;

public class HooverStoriesTest  extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
          .useStoryLoader(new LoadFromClasspath(this.getClass()))
          .useStoryReporterBuilder(new StoryReporterBuilder()
            .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
            .withFormats(CONSOLE));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
    	ArrayList<Steps> stepFileList = new ArrayList<Steps>();
    	stepFileList.add(new HooverStorySteps());
        return new InstanceStepsFactory(configuration(),stepFileList);
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("functionaltests/HooverStorySteps.story");
    }

}
