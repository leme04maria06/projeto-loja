package com.ifsp.projetoLojaMakeup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.projetoLojaMakeup.model.Usuario;
import com.ifsp.projetoLojaMakeup.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    String msg;
    //abre form cadastro
    @GetMapping("/cadastro")
    public String cadastroFormulario(){
        return "cadastro";
    }

    //formulario
    @PostMapping("/cadastro")
    public String cadastrar(Model model,Usuario usuario){
        if (usuarioService.buscarPorEmail(usuario.getEmail())!=null) {
            msg = "Email já cadastrado";
            model.addAttribute("msgErro", msg);
            return "cadastro";
        }

        usuario.setPerfil("CLIENTE");
        usuarioService.salvar(usuario);

        return "redirect:/login";
    }

    //parte de login
    @PostMapping("/login")
    public String login(@RequestParam String senha,
                    @RequestParam String email,
                    HttpSession sessao,
                    Model model){

        Usuario usuarioBanco = usuarioService.buscarPorEmail(email);

        if(usuarioBanco == null){
            msg = "Usuário não cadastrado";
            model.addAttribute("msgErro", msg);
            return "login";
        }

        if(!usuarioBanco.getSenha().equals(senha)){
            msg = "Senha incorreta";
            model.addAttribute("msgErro", msg);
            return "login";
        }

        sessao.setAttribute("usuario", usuarioBanco);

        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(HttpSession sessao){
        Usuario usuario =(Usuario) sessao.getAttribute("usuario");
        if (usuario==null) {
            return "redirect:/login";
        }
        if (!usuario.getPerfil().equals("ADMIN")) {
            return "redirect:/";
        }
        return "admin/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession sessao){
        sessao.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model,HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        List<Usuario> listaUsuarios = usuarioService.listarTodos();
        model.addAttribute("lista",listaUsuarios);
        return "admin/listarUsuarios";
    }

    @GetMapping("/admin/usuario/editar/{id}")
    public String editarUsuarios(@PathVariable Long id, Model model,HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "admin/editarUsuario";
    }

    @PostMapping("/admin/usuario/atualizar")
    public String atualizarUsuario(Usuario usuario, HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        usuarioService.atualizar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/admin/usuario/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        usuarioService.deletar(id);
        return "redirect:/admin/usuarios";
    }

    private boolean usuarioNaoEhAdmin(HttpSession sessao){

        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        if(usuario == null){
            return true;
        }

        return !usuario.getPerfil().equals("ADMIN");
    }
}
