package att.g5.hopital_app_attouch.web;

import att.g5.hopital_app_attouch.entities.Patient;
import att.g5.hopital_app_attouch.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index (Model model,
                         @RequestParam(name="page",defaultValue = "0") int page,
                         @RequestParam(name="keyword",defaultValue = "") String kw,
                         @RequestParam(name="size",defaultValue = "4") int size)
    {
        Page<Patient> pagePatient=patientRepository.findByNomContains(kw,PageRequest.of(page,size));
        model.addAttribute("listepatient",pagePatient.getContent());
        model.addAttribute("pages",new int[pagePatient.getTotalPages()]);
        model.addAttribute("Current page",page);
        model.addAttribute("keyword",kw);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }


}
