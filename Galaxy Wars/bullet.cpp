#include "bullet.h"
#include "sgg/graphics.h"
#include "gamestate.h"
Bullet::Bullet(float x, float y) : GameObject("Bullet") {
	m_pos_x = x;
	m_pos_y = y;
}
void Bullet::update(float dt) {
	m_pos_y -= m_velocity * dt / 1000.0f;
	GameObject::update(dt);
}
void Bullet::draw() {
	m_bullet_brush.texture = m_state->getFullAssetPath("bullet.png");
	m_bullet_brush.outline_opacity = 0.0f;
	drawRect(m_pos_x, m_pos_y, 0.3f, 0.4f, m_bullet_brush);
}
bool Bullet::outOfBounds(float cW, float cH) {
	return m_pos_y < 0 || m_pos_y > cH || m_pos_x < 0 || m_pos_x > cW;
}

Disk Bullet::getCollisionHull() const
{
	Disk disk;
	disk.cx = m_pos_x;
	disk.cy = m_pos_y;
	disk.radius = 0.3f / 2.0f;
	return disk;
}
