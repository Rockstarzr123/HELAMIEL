/* ============================================
   HELAMIEL — Panel Administrativo
   JS del layout general (sidebar / navbar)
   ============================================ */
document.addEventListener('DOMContentLoaded', () => {
  const sidebar = document.getElementById('adminSidebar');
  const backdrop = document.getElementById('sidebarBackdrop');
  const toggleBtn = document.getElementById('btnSidebarToggle');

  if (!sidebar || !backdrop || !toggleBtn) {
    return;
  }

  const abrirSidebar = () => {
    sidebar.classList.add('open');
    backdrop.classList.add('show');
  };

  const cerrarSidebar = () => {
    sidebar.classList.remove('open');
    backdrop.classList.remove('show');
  };

  toggleBtn.addEventListener('click', () => {
    sidebar.classList.contains('open') ? cerrarSidebar() : abrirSidebar();
  });

  backdrop.addEventListener('click', cerrarSidebar);

  sidebar.querySelectorAll('a').forEach((enlace) => {
    enlace.addEventListener('click', cerrarSidebar);
  });
});
