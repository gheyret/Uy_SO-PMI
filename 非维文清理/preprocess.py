# -*- coding: utf-8 -*-
"""
Created on Thu Dec 31 11:52:22 2015

@author: Administrator
"""

    
def create_uyCharacter(character_file='../data/UyCharacter.txt'):
    #创建维吾尔文字母表
    characters = ['ئ', 'م', 'ن', 'ب', 'ۈ', 'غ', 'ش', 'ز', 'ل', 'ك', 'ۆ', 'ق', 'ج', 'ى', 'خ',\
    'ە', 'گ', 'ا', 'ف', 'د', 'ژ', 'س', 'ھ', 'پ', 'و', 'ڭ', 'ۇ', 'ي', 'ت', 'ر', 'ې', 'ۋ', 'چ', '!', '؟', ' ']
    with open(character_file, 'wt', encoding='utf-8') as f:
        s = ''.join(characters)
        f.write(s)

def clearOtherCharacters(fileName,  character_file='../data/UyCharacter.txt'):
    #去除非维吾尔文字符，去掉停用词    
    with open(character_file, encoding='utf-8') as f:
        cs = f.read()
    characters = list(cs)
    wFileName = '../data/new_'+fileName
    fileName = '../data/' + fileName
    wFile = open(wFileName, 'wt', encoding='utf-8')
    with open(fileName, encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            if '!' in line:
                line = line.replace('!', ' ! ')
            if '؟' in line:
                line = line.replace('؟', ' ؟ ')
            new_line = ''
            for c in line:
                if c in characters:
                    new_line += c
                else:
                    new_line += ' '
            new_line = new_line.strip()
            words = new_line.strip().split()
            if len(words)>1:
                wline = ''
                for word in words:
                    wline += word + ' '
                wFile.write(wline + '\n')
    wFile.close()
    
def quChong(fileName):
    #去重
    newFileName = '../data/new_'+fileName
    fileName = '../data/'+fileName
    oldFile = open(fileName, encoding='utf-8')
    lines = [line.strip() for line in oldFile.readlines()]
    lines = list(set(lines))
    oldFile.close()
    with open(newFileName, 'wt', encoding='utf-8') as f:
        for line in lines:
            f.write(line + '\n')
            


