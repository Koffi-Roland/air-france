export enum AuthorizationMailing {
  None = 'N',
  AFKL = 'A',
  AFKLPartners = 'T'
}

export class AuthorizationMailingUtil {
  public static convertToAuthorizationMailing(str: string): AuthorizationMailing {
    switch (str) {
      case 'A': return AuthorizationMailing.AFKL;
      case 'N': return AuthorizationMailing.None;
      case 'T': return AuthorizationMailing.AFKLPartners;
    }
  }
}
