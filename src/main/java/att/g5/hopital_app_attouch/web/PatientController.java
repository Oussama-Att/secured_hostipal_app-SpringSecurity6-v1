package att.g5.hopital_app_attouch.web;


import att.g5.hopital_app_attouch.entities.Patient;
import att.g5.hopital_app_attouch.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword
    ){
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
    @GetMapping("/admin/delete")
    public String delete(
                 @RequestParam(name = "id") Long id,
                 @RequestParam(name = "keyword",defaultValue = "") String keyword,
                 @RequestParam(name = "page",defaultValue = "0") Integer page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/formPatient")
    public String formPatient(Model model,
                              @RequestParam(name = "page", defaultValue = "0") Integer page,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("currentPage", page);
        return "formPatient";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0")  Integer page) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patient);
            return "formPatient";
        }

        patientRepository.save(patient);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(@RequestParam(name = "id") Long id, Model model, Integer page, String keyword) {
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editPatient";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
}
