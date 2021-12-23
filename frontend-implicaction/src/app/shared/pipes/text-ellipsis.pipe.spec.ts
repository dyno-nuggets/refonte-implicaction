import {TextEllipsisPipe} from './text-ellipsis.pipe';

describe('TextEllipsisPipe', () => {
  const pipe = new TextEllipsisPipe();
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('get same string with string shorter than maxLenght', () => {
    expect(pipe.transform('petite string', 40)).toBe('petite string');
  });

  it('get result length equals to maxLength and last char equals to ...', () => {
    const resultString = pipe.transform('une string', 5);
    expect(resultString.length).toBe(5);
    expect(resultString[resultString.length - 1]).toBe('\u2026');
  });
});
