package com.example.premier.Controller;
import com.example.premier.Form.PersonneForm;
import com.example.premier.Functions.Functions;
import com.example.premier.Model.Personne;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.tomcat.util.http.RequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


@Controller
    public class MainController {

        public static Vector<Personne> toutesPersonnes = new Vector<Personne>();

        static {

            toutesPersonnes.add(new Personne("Bill", "Gates",Functions.genererUnId()));
            toutesPersonnes.add(new Personne("Steve", "Jobs",Functions.genererUnId()));
            toutesPersonnes.add(new Personne("Franck", "Kul",Functions.genererUnId()));
        }

        // Injectez (inject) via application.properties.
        @Value("${welcome.message}")
        private String message;


        @Value("${error.message}")
        private String errorMessage;



        @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
        public String index(Model model){
            model.addAttribute("message", message);
            return "listPersonne";
        }

    public String defaultPage(HttpServletRequest request) {
        String url =  request.getRequestURI();

        return "";
    }


        @RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
        public String personList(Model model) {
            model.addAttribute("persons", toutesPersonnes);
            PersonneForm personForm = new PersonneForm();
            model.addAttribute("personForm", personForm);

            return "listPersonne";
        }


        @RequestMapping(value = "/addPerson", method = RequestMethod.GET)
        public String voirPersonne(Model model)
        {
            return "redirect:/personList";
        }


        @RequestMapping(value = {"/addPerson"}, method = RequestMethod.POST)
        public String savePerson(Model model, @ModelAttribute("personForm") PersonneForm personForm) {

            String nom = personForm.getNom();
            String prenom = personForm.getPrenom();

            if (nom != null && nom.length() > 0 && prenom != null && prenom.length() > 0) {
                Personne newPerson = new Personne(nom, prenom,Functions.genererUnId());
                toutesPersonnes.add(newPerson);
                return "redirect:/personList";
            }

            model.addAttribute("errorMessage", errorMessage);
            return "listPersonne";
        }


    @RequestMapping(value = "/modifier/Personne", method = RequestMethod.POST)
    public String modifierPersonne(Model model,
                                   @ModelAttribute("person") PersonneForm personneForm,
                                   @RequestParam(value = "idPersonne") int idPersonne
                                   ) {

            for(int i = 0; i < toutesPersonnes.size(); i++)
            {
                Personne tmp = toutesPersonnes.get(i);

                if(tmp.getId() == idPersonne)
                {
                    tmp.setPrenom(personneForm.getPrenom());
                    tmp.setNom(personneForm.getNom());
                    toutesPersonnes.set(i,tmp);
                }
            }

        return "redirect:/personList";
    }


    @RequestMapping(value = "/supprimer", method = RequestMethod.GET)
    public String supprimer(Model model, @RequestParam(value = "idPersonne") int idPersonne)
    {
        for(int j = 0; j < toutesPersonnes.size(); j++)
        {
            Personne tmp = toutesPersonnes.get(j);
            if(tmp.getId() == idPersonne)
            {
                toutesPersonnes.remove(j);
            }
        }

        return "redirect:/personList";
    }

}