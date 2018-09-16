import { Assignment02Page } from './app.po';

describe('assignment02 App', () => {
  let page: Assignment02Page;

  beforeEach(() => {
    page = new Assignment02Page();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
