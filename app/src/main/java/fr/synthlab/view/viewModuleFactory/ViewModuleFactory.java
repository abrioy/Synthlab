package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleTypes;
import fr.synthlab.model.module.envelope.ModuleEG;
import fr.synthlab.model.module.keyboard.ModuleKEYB;
import fr.synthlab.model.module.mixer.ModuleMIX;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.sequencer.ModuleSEQ;
import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import fr.synthlab.model.module.out.ModuleOUT;
import fr.synthlab.model.module.vca.ModuleVCA;
import fr.synthlab.model.module.vcf.ModuleVCFHP;
import fr.synthlab.model.module.vcf.ModuleVCFLP;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.module.*;
import fr.synthlab.view.module.filter.*;
import fr.synthlab.view.module.input.ViewModuleBRUI;
import fr.synthlab.view.module.input.ViewModuleKEYB;
import fr.synthlab.view.module.output.ViewModuleOUT;
import fr.synthlab.view.module.output.ViewModuleSCOP;
import fr.synthlab.view.module.input.ViewModuleVCOA;

import java.util.logging.Logger;

public class ViewModuleFactory {
    private static final Logger logger = Logger.getLogger(ViewModuleFactory.class.getName());


    public static ViewModule createViewModule(ModuleTypes type, Workbench workbench) {
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
        }
        if (module != null) {
            logger.finer("ViewModule created: " + type.toString());
        } else {
            logger.severe("Unrecognised module type \"" + type.toString() + "\".");
        }
        return module;
    }

    /**
     * create a new module mixer in modele and in view.
     *
     * @param workbench the workbench
     * @return viewModuleMixer
     */
    private static ViewModule createViewModuleMixer(Workbench workbench) {
        Module vco = ModuleFactory.createModule(ModuleTypes.MIX);
        ViewModuleMIX viewMixer = new ViewModuleMIX(workbench);
        viewMixer.setModule(vco);

        viewMixer.setAttenuator1Cmd(() -> ((ModuleMIX) vco).setAttenuation1(viewMixer.getAttenuator1()));
        viewMixer.setAttenuator2Cmd(() -> ((ModuleMIX) vco).setAttenuation2(viewMixer.getAttenuator2()));
        viewMixer.setAttenuator3Cmd(() -> ((ModuleMIX) vco).setAttenuation3(viewMixer.getAttenuator3()));
        viewMixer.setAttenuator4Cmd(() -> ((ModuleMIX) vco).setAttenuation4(viewMixer.getAttenuator4()));
        return viewMixer;
    }

    /**
     * @return a viewModuleVCO attached to its module
     * @param workbench workbench
     */
    private static ViewModule createViewModuleVCO(Workbench workbench) {
        Module vco = ModuleFactory.createModule(ModuleTypes.VCOA);
        ViewModuleVCOA viewVco = new ViewModuleVCOA(workbench);
        viewVco.setModule(vco);

        viewVco.setChangeFreqCommand(() -> ((ModuleVCOA) vco).setFrequency(viewVco.getFreq()));

        viewVco.setChangeShapeCommand(() -> ((ModuleVCOA) vco).setShape(viewVco.getSelectedShape()));

        return viewVco;
    }

    private static ViewModule createViewModuleVCA(Workbench workbench) {
        Module vca = ModuleFactory.createModule(ModuleTypes.VCA);
        ViewModuleVCA viewVca = new ViewModuleVCA(workbench);
        viewVca.setModule(vca);
        viewVca.setChangeAmpliCommand(() -> ((ModuleVCA) vca).setAttenuation(viewVca.getAmpli()));

        return viewVca;
    }

    private static ViewModule createViewModuleOut(Workbench workbench) {
        Module out = ModuleFactory.createModule(ModuleTypes.OUT);
        ViewModuleOUT viewOut = new ViewModuleOUT(workbench);
        viewOut.setModule(out);
        viewOut.setVolumeCommand(() -> ((ModuleOUT) out).setAttenuation(viewOut.getPicker().getValue()));
        viewOut.setMuteCommand(() -> ((ModuleOUT) out).setMute(viewOut.isMute()));
        viewOut.setRecordCommand(() -> {
                    if (viewOut.getPickedFile() != null)
                        ((ModuleOUT) out).setRecording(viewOut.isRecording(), viewOut.getPickedFile());

                }
        );

        return viewOut;
    }
    private static ViewModule createViewModuleWhiteNoise(Workbench workbench) {
        Module brui = ModuleFactory.createModule(ModuleTypes.BRUI);
        ViewModuleBRUI viewNoise = new ViewModuleBRUI(workbench);
        viewNoise.setModule(brui);
        //viewNoise.setVolumeCommand(() -> ((ModuleOUT) out).setAttenuation(viewNoise.getPicker().getValue()));
        //viewNoise.setMuteCommand(() -> ((ModuleOUT) out).setMute(viewNoise.isMute()));

        return viewNoise;
    }

    private static ViewModule createViewModuleOscilloscope(Workbench workbench) {
        Module scop = ModuleFactory.createModule(ModuleTypes.SCOP);
        ViewModuleSCOP viewScop = new ViewModuleSCOP(workbench);
        viewScop.setModule(scop);
        viewScop.setPickerCommand(() -> ((ModuleSCOP) scop).setScale(viewScop.getScale()));

        viewScop.getOscilloscopeDrawing().setModuleOscilloscope((ModuleSCOP) scop);

        return viewScop;
    }

    private static ViewModule createViewModuleREP(Workbench workbench) {
        Module rep = ModuleFactory.createModule(ModuleTypes.REP);
        ViewModuleREP viewREP = new ViewModuleREP(workbench);
        viewREP.setModule(rep);

        return viewREP;
    }

    private static ViewModule createViewModuleEG(Workbench workbench) {
        Module eg = ModuleFactory.createModule(ModuleTypes.EG);
        ViewModuleEG viewEG = new ViewModuleEG(workbench);
        viewEG.setModule(eg);

        viewEG.setChangeAttackCommand(() -> ((ModuleEG) eg).setAttack(viewEG.getAttack()));

        viewEG.setChangeDecayCommand(() -> ((ModuleEG) eg).setDecay(viewEG.getDecay()));

        viewEG.setChangeSustainCommand(() -> ((ModuleEG) eg).setSustain(viewEG.getSustain()));

        viewEG.setChangeReleaseCommand(() -> ((ModuleEG) eg).setRelease(viewEG.getRelease()));

        return viewEG;
    }

    private static ViewModule createViewModuleVCFLP(Workbench workbench) {
        Module vcflp = ModuleFactory.createModule(ModuleTypes.VCFLP);
        ViewModuleVCFLP viewVcflp = new ViewModuleVCFLP(workbench);
        viewVcflp.setModule(vcflp);
        viewVcflp.setChangeThresholdCommand(() -> ((ModuleVCFLP) vcflp).setF0(viewVcflp.getThreshold()));
        viewVcflp.setChangeResonanceCommand(() -> ((ModuleVCFLP) vcflp).setResonance(viewVcflp.getResonance()));


        return viewVcflp;
    }

    private static ViewModule createViewModuleVCFHP(Workbench workbench) {
        Module vcfhp = ModuleFactory.createModule(ModuleTypes.VCFHP);
        ViewModuleVCFHP viewVcfhp = new ViewModuleVCFHP(workbench);
        viewVcfhp.setModule(vcfhp);
        viewVcfhp.setChangeThresholdCommand(() -> ((ModuleVCFHP) vcfhp).setF0(viewVcfhp.getThreshold()));

        return viewVcfhp;
    }

    private static ViewModule createViewModuleKEYB(Workbench workbench) {
        Module keyb = ModuleFactory.createModule(ModuleTypes.KEYB);
        ViewModuleKEYB viewKEYB = new ViewModuleKEYB(workbench);
        viewKEYB.setModule(keyb);

        viewKEYB.setKeyPressedCommand(() -> ((ModuleKEYB) keyb).pressKey(viewKEYB.getNotePressed()));
        viewKEYB.setKeyReleasedCommand(() -> ((ModuleKEYB) keyb).releaseKey());
        viewKEYB.setOctaveChangeCommand(() -> ((ModuleKEYB) keyb).changeOctave(viewKEYB.getOctave()));

        viewKEYB.setOnMouseClicked(event -> {
            viewKEYB.requestFocus();
        });

        return viewKEYB;
    }

    private static ViewModule createViewModuleSEQ(Workbench workbench) {
        Module seq = ModuleFactory.createModule(ModuleTypes.SEQ);
        ViewModuleSEQ viewSEQ = new ViewModuleSEQ(workbench);
        viewSEQ.setModule(seq);

        viewSEQ.setResetCommand(() -> ((ModuleSEQ) seq).resetStep());

        return viewSEQ;

    }
}
