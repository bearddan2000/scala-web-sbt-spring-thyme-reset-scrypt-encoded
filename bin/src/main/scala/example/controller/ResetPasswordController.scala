package example.controller;

import example.model.User;
import example.service.SecurityService;
import example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
class ResetPasswordController @Autowired()(
    securityService :SecurityService
    , userService :UserService
  )
{

	@GetMapping(Array("/reset"))
	def reset(model: Model): String =
	{
		model.addAttribute("resetForm", new User("", "", ""));
		return "reset";
	}

  @PostMapping(Array("/reset"))
  def reset(@ModelAttribute("resetForm") userForm: User): String = {
      val user: User = userService.resetPassword(userForm.username, userForm.password);

      if (user == null) {
        return "redirect:/register";
      }

      securityService.autoLogin(user.username, user.password);

      return "redirect:/user";
  }
}
