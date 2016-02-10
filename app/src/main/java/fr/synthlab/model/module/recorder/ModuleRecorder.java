package fr.synthlab.model.module.recorder;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.util.WaveRecorder;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.Port;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ModuleRecorder implements Module {

    protected Collection<Port> ports = new ArrayList<>();

    private InputPort input;
    private PassThrough passThrough = new PassThrough();
    private boolean recording = false;

    private Synthesizer synthesizer;

    private WaveRecorder waveRecorder;

    public ModuleRecorder(Synthesizer synthesizer) {
        this.synthesizer = synthesizer;
        synthesizer.add(passThrough);

        input = new InputPort("in", this, passThrough.input);
        ports.add(input);
    }

    public void setRecording(boolean recording) {
        this.recording = recording;

        try {
            if (recording) {
                DateFormat dateFormat = new SimpleDateFormat("recordings/yyyy-MM-dd_HH_mm_ss");
                File file = new File(dateFormat.format(new Date()) + ".wav");
                waveRecorder = new WaveRecorder(synthesizer, file);
                passThrough.output.connect(waveRecorder.getInput());

                waveRecorder.start();
            } else {
                waveRecorder.stop();

                passThrough.output.disconnectAll();

                waveRecorder.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        passThrough.start();
    }

    @Override
    public void stop() {
        passThrough.stop();
    }

    @Override
    public void update() {
        if (input.getConnected() == null) {
            setRecording(false);
        }
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.REC;
    }
}
