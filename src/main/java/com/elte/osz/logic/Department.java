/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic;

/**
 *
 * @author Tóth Ákos
 */
public enum Department {
    IK {
        @Override
        public String toString(){
            return "IK";
        }
    },    
    TTK{
        @Override
        public String toString(){
            return "TTK";
        }
    },    
    PPK{
        @Override
        public String toString(){
            return "IK";
        }
    },    
    TATK{
        @Override
        public String toString(){
            return "TáTK";
        }
    },    
    TOK{
        @Override
        public String toString(){
            return "TÓK";
        }
    },    
    AJK{
        @Override
        public String toString(){
            return "ÁJK";
        }
    },    
    BTK{
        @Override
        public String toString(){
            return "BTK";
        }
    },    
    BGGYK{
        @Override
        public String toString(){
            return "BGGyK";
        }
    }    
}
