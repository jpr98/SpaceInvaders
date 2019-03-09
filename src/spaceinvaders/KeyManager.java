/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class KeyManager implements KeyListener {
    // movement controls
    public boolean left;
    public boolean right;
    public boolean up;
    // game controls
    public boolean p;
    public boolean r;
    public boolean g;
    public boolean c;

    private boolean keys[];

    public KeyManager() {
        keys = new boolean[256];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (p) {
                keys[e.getKeyCode()] = false;
            } else {
                keys[e.getKeyCode()] = true;
            }
        } else{
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_P)
            keys[e.getKeyCode()] = false;
    }

    public void tick() {
        // setting values of pressed keys to directions
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        up = keys[KeyEvent.VK_UP];
        p = keys[KeyEvent.VK_P];
        r = keys[KeyEvent.VK_R];
        g = keys[KeyEvent.VK_G];
        c = keys[KeyEvent.VK_C];
    }
}