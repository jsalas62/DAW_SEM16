package com.reservatec.backendreservatec.webs;

import com.reservatec.backendreservatec.entities.Reserva;
import com.reservatec.backendreservatec.entities.Usuario;
import com.reservatec.backendreservatec.services.CampoService;
import com.reservatec.backendreservatec.services.HorarioService;
import com.reservatec.backendreservatec.services.ReservaService;
import com.reservatec.backendreservatec.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService reservaService;
    private final CampoService campoService;
    private final HorarioService horarioService;
    private final AuthenticationService authenticationService;

    @Autowired
    public ReservaController(ReservaService reservaService,
                             CampoService campoService,
                             HorarioService horarioService,
                             AuthenticationService authenticationService) {
        this.reservaService = reservaService;
        this.campoService = campoService;
        this.horarioService = horarioService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/nueva")
    public String getReservaForm(OAuth2AuthenticationToken token, Model model, Authentication authentication) {
        if (!authenticationService.isAuthenticated(authentication)) {
            return "redirect:/login";
        }

        Optional<Usuario> usuario = authenticationService.getUserFromToken(token)
                .map(authenticationService::convertToEntity);
        if (usuario.isEmpty()) {
            return "redirect:/user/form";
        }

        model.addAttribute("reserva", new Reserva());
        model.addAttribute("campos", campoService.findAllCampos());
        model.addAttribute("horarios", horarioService.findAllHorarios());
        return "reservaForm";
    }

    @PostMapping("/nueva")
    public String submitReservaForm(@ModelAttribute Reserva reserva, OAuth2AuthenticationToken token, Model model) {
        Optional<Usuario> usuario = authenticationService.getUserFromToken(token)
                .map(authenticationService::convertToEntity);
        if (usuario.isEmpty()) {
            return "redirect:/user/form";
        }

        reserva.setUsuario(usuario.get());

        try {
            reservaService.crearReserva(reserva);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("campos", campoService.findAllCampos());
            model.addAttribute("horarios", horarioService.findAllHorarios());
            return "reservaForm";
        }

        return "redirect:/reserva/success";
    }

    @GetMapping("/misreservas")
    public String getMisReservas(OAuth2AuthenticationToken token, Model model, Authentication authentication) {
        if (!authenticationService.isAuthenticated(authentication)) {
            return "redirect:/login";
        }

        Optional<Usuario> usuario = authenticationService.getUserFromToken(token)
                .map(authenticationService::convertToEntity);
        if (usuario.isEmpty()) {
            return "redirect:/user/form";
        }

        List<Reserva> reservas = reservaService.findReservasByUsuario(usuario.get().getId());
        model.addAttribute("reservas", reservas);
        return "misReservas";
    }

    @GetMapping("/success")
    public String successPage() {
        return "successReserva";
    }
}
