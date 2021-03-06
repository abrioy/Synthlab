package fr.synthlab.view.module;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleFactory;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.envelope.ModuleEG;
import fr.synthlab.model.module.keyboard.ModuleKEYB;
import fr.synthlab.model.module.mixer.ModuleMIX;
import fr.synthlab.model.module.sequencer.ModuleSEQ;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.out.ModuleOUT;
import fr.synthlab.model.module.vca.ModuleVCA;
import fr.synthlab.model.module.vcf.ModuleVCFHP;
import fr.synthlab.model.module.vcf.ModuleVCFLP;
import fr.synthlab.model.module.vcoa.ModuleVCOA;

import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.module.filter.ViewModuleSEQImpl;
import fr.synthlab.view.module.filter.ViewModuleEGImpl;
import fr.synthlab.view.module.filter.ViewModuleMIXImpl;
import fr.synthlab.view.module.filter.ViewModuleREPImpl;
import fr.synthlab.view.module.filter.ViewModuleVCAImpl;
import fr.synthlab.view.module.filter.ViewModuleVCFHPImpl;
import fr.synthlab.view.module.filter.ViewModuleVCFLPImpl;
import fr.synthlab.view.module.input.ViewModuleBRUIImpl;
import fr.synthlab.view.module.input.ViewModuleKEYBImpl;
import fr.synthlab.view.module.input.ViewModuleVCOAImpl;
import fr.synthlab.view.module.output.ViewModuleOUTImpl;
import fr.synthlab.view.module.output.ViewModuleSCOPImpl;

import java.io.File;
import java.util.logging.Logger;

/**
 * factory for view module.
 */
public class ViewModuleFactory {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleFactory.class.getName());

    /**
     * create view module.
     * @param type of module
     * @param workbench current workbench
     * @return new view module
     */
    public static ViewModule createViewModule(
            final ModuleType type, final Workbench workbench) {
        ViewModule module = null;
        switch (type) {
            case VCOA:
                module = createViewModuleVCO(workbench);
                break;
            case VCA:
                module = createViewModuleVCA(workbench);
                break;
            case OUT:
                module = createViewModuleOut(workbench);
                break;
            case SCOP:
                module = createViewModuleOscilloscope(workbench);
                break;
            case REP:
                module = createViewModuleREP(workbench);
                break;
            case EG:
                module = createViewModuleEG(workbench);
                break;
            case VCFLP:
                module = createViewModuleVCFLP(workbench);
                break;
            case VCFHP:
                module = createViewModuleVCFHP(workbench);
                break;
            case MIX:
                module = createViewModuleMixer(workbench);
                break;
            case BRUI:
                module = createViewModuleWhiteNoise(workbench);
                break;
            case KEYB:
                module = createViewModuleKEYB(workbench);
                break;
            case SEQ:
                module = createViewModuleSEQ(workbench);
                break;
            default:
                break;
        }
        if (module != null) {
            LOGGER.finer("ViewModule created: " + type.toString());
        } else {
            LOGGER.severe("Unrecognised module type \""
                    + type.toString() + "\".");
        }
        return module;
    }

    /**
     * create a new module mixer in modele and in view.
     *
     * @param workbench the workbench
     * @return viewModuleMixer
     */
    private static ViewModule createViewModuleMixer(final Workbench workbench) {
        Module vco = ModuleFactory.createModule(ModuleType.MIX);
        ViewModuleMIXImpl viewMixer = new ViewModuleMIXImpl(workbench);
        viewMixer.setModule(vco);

        viewMixer.setAttenuator1Cmd(() -> ((ModuleMIX) vco)
                .setAttenuation1(viewMixer.getAttenuator1()));
        viewMixer.setAttenuator2Cmd(() -> ((ModuleMIX) vco)
                .setAttenuation2(viewMixer.getAttenuator2()));
        viewMixer.setAttenuator3Cmd(() -> ((ModuleMIX) vco)
                .setAttenuation3(viewMixer.getAttenuator3()));
        viewMixer.setAttenuator4Cmd(() -> ((ModuleMIX) vco)
                .setAttenuation4(viewMixer.getAttenuator4()));
        return viewMixer;
    }

    /**
     * @return a viewModuleVCO attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleVCO(final Workbench workbench) {
        Module vco = ModuleFactory.createModule(ModuleType.VCOA);
        ViewModuleVCOAImpl viewVco = new ViewModuleVCOAImpl(workbench);
        viewVco.setModule(vco);

        viewVco.setChangeFreqCommand(() -> ((ModuleVCOA) vco)
                .setFrequency(viewVco.getFreq()));
        viewVco.setChangeShapeCommand(() -> ((ModuleVCOA) vco)
                .setShape(viewVco.getSelectedShape()));
        return viewVco;
    }

    /**
     * @return a viewModuleVCA attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleVCA(final Workbench workbench) {
        Module vca = ModuleFactory.createModule(ModuleType.VCA);
        ViewModuleVCAImpl viewVca = new ViewModuleVCAImpl(workbench);
        viewVca.setModule(vca);
        viewVca.setChangeAmpliCommand(() -> ((ModuleVCA) vca)
                .setAttenuation(viewVca.getAmpli()));

        return viewVca;
    }

    /**
     * @return a viewModuleOut attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleOut(final Workbench workbench) {
        Module out = ModuleFactory.createModule(ModuleType.OUT);
        ViewModuleOUTImpl viewOut = new ViewModuleOUTImpl(workbench);
        viewOut.setModule(out);
        viewOut.setVolumeCommand(() -> ((ModuleOUT) out)
                .setAttenuation(viewOut.getPicker().getValue()));
        viewOut.setMuteCommand(() -> ((ModuleOUT) out)
                .setMute(viewOut.isMute()));
        viewOut.setRecordCommand(() -> {
            if (!((ModuleOUT) out).isRecording()) {
                File recordingFile = viewOut.getRecordingFile();
                if (recordingFile == null) {
                    viewOut.setIsRecording(false);
                } else {
                    ((ModuleOUT) out).setRecording(
                            viewOut.isRecording(), recordingFile);
                }
            } else {
                viewOut.setIsRecording(false);
                ((ModuleOUT) out).setRecording(viewOut.isRecording(), null);
            }
        });
        return viewOut;
    }

    /**
     * @return a viewModuleWhiteNoise attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleWhiteNoise(
            final Workbench workbench) {
        Module brui = ModuleFactory.createModule(ModuleType.BRUI);
        ViewModuleBRUIImpl viewNoise = new ViewModuleBRUIImpl(workbench);
        viewNoise.setModule(brui);

        return viewNoise;
    }

    /**
     * @return a viewModuleOscilloscope attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleOscilloscope(
            final Workbench workbench) {
        Module scop = ModuleFactory.createModule(ModuleType.SCOP);
        ViewModuleSCOPImpl viewScop = new ViewModuleSCOPImpl(workbench);
        viewScop.setModule(scop);
        viewScop.setPickerCommand(() -> ((ModuleSCOP) scop)
                .setScale(viewScop.getScale()));

        viewScop.getOscilloscopeDrawing()
                .setModuleOscilloscope((ModuleSCOP) scop);

        return viewScop;
    }

    /**
     * @return a viewModuleRep attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleREP(final Workbench workbench) {
        Module rep = ModuleFactory.createModule(ModuleType.REP);
        ViewModuleREPImpl viewREP = new ViewModuleREPImpl(workbench);
        viewREP.setModule(rep);

        return viewREP;
    }

    /**
     * @return a viewModuleEG attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleEG(final Workbench workbench) {
        Module eg = ModuleFactory.createModule(ModuleType.EG);
        ViewModuleEGImpl viewEG = new ViewModuleEGImpl(workbench);
        viewEG.setModule(eg);

        viewEG.setChangeAttackCommand(() -> ((ModuleEG) eg)
                .setAttack(viewEG.getAttack()));
        viewEG.setChangeDecayCommand(() -> ((ModuleEG) eg)
                .setDecay(viewEG.getDecay()));
        viewEG.setChangeSustainCommand(() -> ((ModuleEG) eg)
                .setSustain(viewEG.getSustain()));
        viewEG.setChangeReleaseCommand(() -> ((ModuleEG) eg)
                .setRelease(viewEG.getRelease()));
        return viewEG;
    }

    /**
     * @return a viewModuleVCFLP attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleVCFLP(final Workbench workbench) {
        Module vcflp = ModuleFactory.createModule(ModuleType.VCFLP);
        ViewModuleVCFLPImpl viewVcflp = new ViewModuleVCFLPImpl(workbench);
        viewVcflp.setModule(vcflp);
        viewVcflp.setChangeThresholdCommand(() -> ((ModuleVCFLP) vcflp)
                .setF0(viewVcflp.getThreshold()));
        viewVcflp.setChangeResonanceCommand(() -> ((ModuleVCFLP) vcflp)
                .setResonance(viewVcflp.getResonance()));

        return viewVcflp;
    }

    /**
     * @return a viewModuleVCLHP attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleVCFHP(final Workbench workbench) {
        Module vcfhp = ModuleFactory.createModule(ModuleType.VCFHP);
        ViewModuleVCFHPImpl viewVcfhp = new ViewModuleVCFHPImpl(workbench);
        viewVcfhp.setModule(vcfhp);
        viewVcfhp.setChangeThresholdCommand(() -> ((ModuleVCFHP) vcfhp)
                .setF0(viewVcfhp.getThreshold()));
        return viewVcfhp;
    }

    /**
     * @return a viewModuleKeyb attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleKEYB(final Workbench workbench) {
        Module keyb = ModuleFactory.createModule(ModuleType.KEYB);
        ViewModuleKEYBImpl viewKEYB = new ViewModuleKEYBImpl(workbench);
        viewKEYB.setModule(keyb);
        viewKEYB.setKeyPressedCommand(() -> ((ModuleKEYB) keyb)
                .pressKey(viewKEYB.getNotePressed()));
        viewKEYB.setKeyReleasedCommand(() -> ((ModuleKEYB) keyb)
                .releaseKey(viewKEYB.getLastKeyReleased()));
        viewKEYB.setOctaveChangeCommand(() -> ((ModuleKEYB) keyb)
                .changeOctave(viewKEYB.getOctave()));

        viewKEYB.setOnMouseClicked(event -> viewKEYB.requestFocus());

        return viewKEYB;
    }

    /**
     * @return a viewModuleSEQ attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleSEQ(final Workbench workbench) {
        Module seq = ModuleFactory.createModule(ModuleType.SEQ);
        ViewModuleSEQImpl viewSEQ = new ViewModuleSEQImpl(workbench);
        viewSEQ.setModule(seq);

        viewSEQ.setResetCommand(((ModuleSEQ) seq)::reset);
        viewSEQ.setChangeStep1Command(() -> ((ModuleSEQ) seq)
                .setStepValue(0, viewSEQ.getStepValue(0)));
        viewSEQ.setChangeStep2Command(() -> ((ModuleSEQ) seq)
                .setStepValue(1, viewSEQ.getStepValue(1)));
        viewSEQ.setChangeStep3Command(() -> ((ModuleSEQ) seq)
                .setStepValue(2, viewSEQ.getStepValue(2)));
        viewSEQ.setChangeStep4Command(() -> ((ModuleSEQ) seq)
                .setStepValue(3, viewSEQ.getStepValue(3)));
        viewSEQ.setChangeStep5Command(() -> ((ModuleSEQ) seq)
                .setStepValue(4, viewSEQ.getStepValue(4)));
        viewSEQ.setChangeStep6Command(() -> ((ModuleSEQ) seq)
                .setStepValue(5, viewSEQ.getStepValue(5)));
        viewSEQ.setChangeStep7Command(() -> ((ModuleSEQ) seq)
                .setStepValue(6, viewSEQ.getStepValue(6)));
        viewSEQ.setChangeStep8Command(() -> ((ModuleSEQ) seq)
                .setStepValue(7, viewSEQ.getStepValue(7)));

        ((ModuleSEQ) seq).addObserver(viewSEQ);
        return viewSEQ;
    }
}
