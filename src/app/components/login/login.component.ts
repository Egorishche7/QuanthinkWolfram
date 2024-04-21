import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LanguageService } from '../../services/language.service';
import { Subscription } from 'rxjs';
import { EventEmitter } from '@angular/core';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  host: { class: 'orange-form' }
})
export class LoginComponent implements OnInit {
languageChanged: EventEmitter<string> = new EventEmitter<string>();
  loginForm: FormGroup;
  private languageSubscription: Subscription | undefined;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private msgService: MessageService,
    private languageService: LanguageService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
  this.languageSubscription = this.languageService.selectedLanguageChanged.subscribe(() => {
    // Update translations on the login form
    // For example, you can update the form header and button labels
    this.loginForm.patchValue({
      header: this.languageService.getTranslation('Sign In'),
      emailLabel: this.languageService.getTranslation('Email'),
      passwordLabel: this.languageService.getTranslation('Password'),
      signInButtonLabel: this.languageService.getTranslation('SIGN IN'),
      createAccountLinkLabel: this.languageService.getTranslation("Don't have an account? Create one.")
    });
  });
}

  ngOnDestroy() {
    this.languageSubscription?.unsubscribe();
  }

  get email() {
    return this.loginForm.controls['email'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  loginUser() {
    const { email, password } = this.loginForm.value;
    this.authService.login({ email, password }).subscribe(
      response => {
        console.log(response);
        const userId = response.id;
        localStorage.setItem('email', email);
        localStorage.setItem('userId', userId);
        this.router.navigate(['/home']);
      },
      error => {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: "Incorrect email or password" });
      }
    );
  }

  changeLanguage(language: string) {
  this.languageService.setLanguage(language);
  this.languageChanged.emit(language);
}
}