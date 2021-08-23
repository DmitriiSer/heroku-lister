import { AuthService } from './shared/services/auth.service';

export function appInitializer(authService: AuthService) {
    return () => {
        return new Promise<any>(resolve => {
            authService.initFacebook(resolve);
        });
    }
}