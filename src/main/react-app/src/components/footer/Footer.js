import moment from 'moment'

const Footer = () => {
  return (
    <footer className='bg-dark text-white-50 text-center p-3 position-fixed bottom-0 w-100 overflow-hidden'>
      <p className='mb-0'>Copyright &copy; Sell My Picture {moment(new Date()).format('YYYY')}</p>
    </footer>
  )
}

export default Footer